package com.example.liquidui.renderer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.liquidui.model.UIModel
import com.example.liquidui.utils.ImageLoader
import me.relex.circleindicator.CircleIndicator3

// LiquidUIRenderer.kt - Full PhonePe Home Clone Renderer with Borders, Padding, Elevation

object LiquidUIRenderer {
    fun render(context: Context, model: UIModel, parent: ViewGroup) {
        when (model.type) {
            "vertical_stack" -> {
                val layout = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(model.padding, model.padding, model.padding, model.padding)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                model.children?.forEach { child ->
                    render(context, child, layout)
                }
                parent.addView(layout)
            }

            "banner" -> {
                val frameLayout = FrameLayout(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        200 // adjust banner height here
                    )
                }

                val imageView = ImageView(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                ImageLoader.load(model.imageUrl, imageView)
                frameLayout.addView(imageView)

                // Optional Text Overlay (if title or text exists)
                model.title?.let { title ->
                    val textView = TextView(context).apply {
                        text = title
                        setTextColor(Color.WHITE)
                        textSize = 16f
                        gravity = Gravity.CENTER
                        setShadowLayer(4f, 0f, 0f, Color.BLACK) // shadow for readability
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            Gravity.CENTER
                        )
                    }
                    frameLayout.addView(textView)
                }

                model.action?.let { action ->
                    if (action.type == "navigate_intent") {
                        frameLayout.setOnClickListener {
                            try {
                                val intent = Intent()
                                intent.setClassName(context, action.screen ?: "")
                                action.extras?.forEach { (key, value) ->
                                    intent.putExtra(key, value)
                                }
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Activity not found${e.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }


                    parent.addView(frameLayout)
                }
            }

            "label" -> { // Simple label/text
                val textView = TextView(context).apply {
                    text = model.text
                    textSize = model.textSize?.toFloat() ?: 16f
                    setTextColor(Color.parseColor(model.textColor ?: "#000000"))
                    setPadding(16, 16, 16, 8)
                }
                parent.addView(textView)
            }

            "label_with_action" -> { // Title with View All option
                val layout = LinearLayout(context).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    setPadding(16, 16, 16, 8)
                }
                val label = TextView(context).apply {
                    text = model.text
                    textSize = 16f
                    setTypeface(null, Typeface.BOLD)
                }
                val action = TextView(context).apply {
                    text = model.actionText
                    setTextColor(Color.parseColor("#6200EE"))
                    setOnClickListener {
                        Toast.makeText(context, "View All Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                layout.addView(label)
                layout.addView(Space(context), LinearLayout.LayoutParams(0, 0, 1f))
                layout.addView(action)
                parent.addView(layout)
            }

            "horizontal_list" -> { // Horizontal RecyclerView
                val recyclerView = RecyclerView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        200
                    )
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = HorizontalAdapter(context, model.children ?: emptyList())
                }
                parent.addView(recyclerView)
            }

            "grid" -> { // Grid with border and elevation
                val grid = GridLayout(context).apply {
                    columnCount = model.columns ?: 4
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                model.children?.forEach { child ->
                    val card = CardView(context).apply {
                        radius = 12f
                        cardElevation = 4f
                        setContentPadding(25, 25, 25, 25)
                        layoutParams = GridLayout.LayoutParams().apply {
                            width =
                                context.resources.displayMetrics.widthPixels / (model.columns ?: 4)
                            setMargins(4, 4, 4, 4)
                        }
                    }
                    val layout = LinearLayout(context).apply {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER
                    }
                    val icon = ImageView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(80, 80)
                    }
                    ImageLoader.load(child.iconUrl, icon)
                    val title = TextView(context).apply {
                        text = child.title
                        gravity = Gravity.CENTER
                        textSize = 12f
                    }
                    layout.addView(icon)
                    layout.addView(title)
                    card.addView(layout)
                    card.setOnClickListener {
                        Toast.makeText(context, "Clicked: ${child.title}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    grid.addView(card)
                }
                parent.addView(grid)
            }

            "carousel" -> {
                val viewPager = ViewPager2(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        600 // increased height
                    )
                    adapter = CarouselAdapter(context, model.children ?: emptyList())
                    clipToPadding = false
                    clipChildren = false
                    offscreenPageLimit = 3
                }

                parent.addView(viewPager)
                val indicator = CircleIndicator3(context).apply {
                    setViewPager(viewPager)
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                        topMargin = 8
                    }
                }

                parent.addView(indicator)

                val handler = Handler(Looper.getMainLooper())
                val runnable = object : Runnable {
                    var currentPage = 0
                    override fun run() {
                        val itemCount = model.children?.size ?: 0
                        if (itemCount > 0) {
                            viewPager.setCurrentItem(currentPage % itemCount, true)
                            currentPage++
                        }
                        handler.postDelayed(this, 3000)
                    }
                }
                handler.post(runnable)

            }

        }
    }
}
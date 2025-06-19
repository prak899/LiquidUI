package com.example.liquidui.renderer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.liquidui.model.UIModel
import com.example.liquidui.utils.ImageLoader
class CarouselAdapter(
    private val context: Context,
    private val items: List<UIModel>
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val imageView = ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Important!
                ViewGroup.LayoutParams.MATCH_PARENT  // Important!
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return CarouselViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        ImageLoader.load(item.imageUrl, holder.imageView)

        holder.itemView.setOnClickListener {
            item.action?.let { action ->
                if (action.type == "navigate_intent") {
                    try {
                        val intent = Intent(context, Class.forName(action.screen!!))
                        action.extras?.forEach { (key, value) ->
                            intent.putExtra(key, value)
                        }
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Error opening screen", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

// HorizontalAdapter.kt - For horizontal RecyclerView items
class HorizontalAdapter(
    private val context: Context,
    private val items: List<UIModel>
) : RecyclerView.Adapter<HorizontalAdapter.Holder>() {

    inner class Holder(val view: LinearLayout) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return Holder(layout)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        val icon = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(80, 80)
        }
        ImageLoader.load(item.iconUrl, icon)
        val title = TextView(context).apply {
            text = item.title
            gravity = Gravity.CENTER
        }
        holder.view.removeAllViews()
        holder.view.addView(icon)
        holder.view.addView(title)
        holder.view.setOnClickListener {
            Toast.makeText(context, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size
}

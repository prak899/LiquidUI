package com.example.liquidui

import android.content.Context
import android.widget.LinearLayout
import com.example.liquidui.model.UIModel
import com.example.liquidui.renderer.LiquidUIRenderer
import com.google.gson.Gson

object LiquidUI {
    fun renderUI(context: Context, jsonString: String, container: LinearLayout) {
        val model = Gson().fromJson(jsonString, UIModel::class.java)
        LiquidUIRenderer.render(context, model, container)
    }
}
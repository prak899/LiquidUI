package com.example.apitesting

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.liquidui.LiquidUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class MainActivity : AppCompatActivity() {
    private var encryption = encr_decr_android()
    private var exportTable = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            val statusBarHeight = resources.getDimensionPixelSize(
                resources.getIdentifier("status_bar_height", "dimen", "android")
            )
            findViewById<LinearLayout>(R.id.root_layout).setPadding(0, statusBarHeight, 0, 0)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                loadSampleApi()
            }
            withContext(Dispatchers.Main) {
//                val textview = findViewById<TextView>(R.id.text_view)
//
//                textview.text = exportTable
            }
        }

        val container = findViewById<LinearLayout>(R.id.root_layout)
        val json = loadJSONFromRaw(this, R.raw.liquid_ui)
        LiquidUI.renderUI(this, json, container)
    }
    private fun loadJSONFromRaw(context: Context, resId: Int): String {
        val inputStream = context.resources.openRawResource(resId)
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun loadSampleApi() {
        Log.d("TAG", "loadSampleApi: here")
        val baseUrl = "https://cspdcl.co.in/saubhagya/Prakash.asmx"
        val nameSpace = "http://tempuri.org/"
        val soapAction = "http://tempuri.org/" + "MA_cons_datatable"
        val methodName = "MA_cons_datatable"
        try {
            val encryptedData = encryption.encrypt(
                "PMSURYAGHAR_PROG_SELECT_DASHBOARD|1||342cnbwdf7*gS|2315645645648651321654ANBC",
                "tjdoi"
            )
            val soapObject = SoapObject(nameSpace, methodName)
            soapObject.addProperty("cons_string", encryptedData)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.setOutputSoapObject(soapObject)
            envelope.dotNet = true
            val httpTransportSE = HttpTransportSE(baseUrl)
            httpTransportSE.call(soapAction, envelope)

            val bodyIn = envelope.bodyIn

            Log.d("TAG", "loadSampleApi: $bodyIn")
            if (bodyIn is SoapObject) {

                val dataSetResponse = bodyIn.getProperty("MA_cons_datatableResult") as SoapObject
                val diffGram = dataSetResponse.getProperty("diffgram") as SoapObject
                val newDataSetRes = diffGram.getProperty("DocumentElement") as SoapObject
                for (i in 0 until newDataSetRes.propertyCount) {
                    val exportTableRes = newDataSetRes.getProperty(i)
                    if (exportTableRes is SoapObject) {
                        Log.d("TAG", "loadSampleApi: $exportTableRes")
                        exportTable = exportTableRes.toString()
                    }
                }
            }
        } catch (err: Exception) {
            Log.d("TAG", "loadSampleApi: ${err.message}")
            err.printStackTrace()
        }
    }
}
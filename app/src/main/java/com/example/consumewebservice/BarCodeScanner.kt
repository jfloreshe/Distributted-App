package com.example.consumewebservice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator;
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.net.URL


class BarCodeScanner : AppCompatActivity() {

    lateinit var scanBtn: Button
    lateinit var formatTxt: TextView
    lateinit var contentTxt: TextView
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code_scanner)
        scanBtn = findViewById<View>(R.id.scan_button) as Button
        formatTxt = findViewById<View>(R.id.scan_format) as TextView
        contentTxt = findViewById<View>(R.id.scan_content) as TextView
        imageView = findViewById<View>(R.id.imageView_inventario) as ImageView

    }
    fun scan(view:View){
        val scanIntegrator = IntentIntegrator(this)
        scanIntegrator.initiateScan()
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        val scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        if (scanningResult != null) {
            val scanContent = "CONTENT: " + scanningResult.contents
            val scanFormat =  "FORMAT: " + scanningResult.formatName
            formatTxt.setText( scanFormat)
            contentTxt.setText( scanContent)
            var path = globalServer + "/media/1575413033408.jpg"
            Picasso.get().load(path).into(imageView)
        }
        else{
            contentTxt.setText("no scan data received")
        }
        //retrieve scan result
    }
}

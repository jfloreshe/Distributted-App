package com.example.consumewebservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuOpciones : AppCompatActivity() {
    lateinit var gotoIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_opciones)
    }
    fun gotoADDInventario(view: View){
        gotoIntent = Intent(this,InventarioActivity::class.java)
        startActivity(gotoIntent)
    }
    fun gotoADDMovimiento(view: View){
        gotoIntent = Intent(this,movimientoActivity::class.java)
        startActivity(gotoIntent)
    }
    fun gotoSCAN(view: View){
        gotoIntent = Intent(this,BarCodeScanner::class.java)
        startActivity(gotoIntent)
    }
    fun gotoADDOtros(view: View){
        //TODO
    }
}

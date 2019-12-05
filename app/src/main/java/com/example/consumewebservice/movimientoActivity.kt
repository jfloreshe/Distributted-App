package com.example.consumewebservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
TODO
*AGREGAR FUNCION PARA RECONOCER UN ELEMENTO MEDIANTE CODIGO BARRAS
*CREAR UN NUEVO CODIGO DE BARRAS POR CADA MOVIMIENTO LINKEANDO EL ELEMENTO Y EL ID_MOVIMIENTO
* CADA MOVIMIENTO DEBE TENER UN ID AUTOGENERADO PROBABLEMENTE, UN ID_ELEMENT UN ID_AMBIENTE FECHA ESTADO OBSERVACION
* CODIGO DE BARRA NUEVO GENERADO
* Por cada inventario nuvo crear un codigo de barra

 */
class movimientoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimiento)
    }

}

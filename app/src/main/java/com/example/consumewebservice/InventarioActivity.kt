package com.example.consumewebservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.EditText

import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView


class InventarioActivity : AppCompatActivity() {
    private lateinit var chapterDetailsJSON: JSONObject
    var client = OkHttpClient()
    var request = OkHttpRequest(client)
    var imagenToUpload = ImageRest()
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    lateinit var image_uri: Uri
    lateinit var foto_full_path:String
    lateinit var foto_name:String

    internal lateinit var codigo: EditText
    internal lateinit var denominacion: EditText
    internal lateinit var marca: EditText
    internal lateinit var modelo: EditText
    internal lateinit var tipo: EditText
    internal lateinit var color: EditText
    internal lateinit var serie: EditText
    internal lateinit var e: EditText
    internal lateinit var id: EditText
    internal lateinit var add: Button
    internal lateinit var takefoto: Button
    internal lateinit var fotopath: EditText
    internal lateinit var imageFoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventarion)

        codigo = findViewById<View>(R.id.editText_codigo) as EditText
        denominacion = findViewById<View>(R.id.editText_denominacion) as EditText
        marca = findViewById<View>(R.id.editText_marca) as EditText
        modelo = findViewById<View>(R.id.editText_modelo) as EditText
        tipo = findViewById<View>(R.id.editText_tipo) as EditText
        color = findViewById<View>(R.id.editText_color) as EditText
        serie = findViewById<View>(R.id.editText_serie) as EditText
        e = findViewById<View>(R.id.editText_e) as EditText
        id = findViewById<View>(R.id.editText_id) as EditText
        add = findViewById<View>(R.id.button_add) as Button
        takefoto = findViewById<View>(R.id.button_tomarfoto) as Button
        fotopath =  findViewById<View>(R.id.editText_fotopath) as EditText
        imageFoto = findViewById<View>(R.id.imageView_foto) as ImageView
    }

    fun send(v: View) {

        //upload image first and then info
        imagenToUpload.uploadFoto(foto_full_path)
        foto_name = "/media/" + foto_name
        //Cambiar el url correcto DEL NUEVO SERVIDOR
        val url = globalServer +"/confirmInventario/"
        val map: HashMap<String,String> = hashMapOf("codigo" to codigo.text.toString(),"denominacion" to denominacion.text.toString()
            ,"marca" to marca.text.toString(),"modelo" to modelo.text.toString(),"tipo" to tipo.text.toString()
            ,"color" to color.text.toString(),"serie" to serie.text.toString(),"e" to e.text.toString(),"id_inventario" to id.text.toString(),"filename" to foto_name)
        request.POST(url,map,object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                runOnUiThread{
                    try{
                        var json = JSONObject(responseData)
                        println("request successfull")
                        if(json.getString("succes")=="True") {
                            Toast.makeText(applicationContext,"Subio correctamente",Toast.LENGTH_LONG).show()
                        }
                        else
                            Toast.makeText(applicationContext,"Inventario ya existe",Toast.LENGTH_LONG).show()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Request failure.")
            }
        })
    }

    fun openCamera(v: View) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera intent
        if(requestCode == IMAGE_CAPTURE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //fotopath.setText(image_uri.toString())//Nos da la version uri del archivo
                foto_full_path = getRealPathFromURI(applicationContext,image_uri)
                foto_name = foto_full_path.substringAfterLast('/')
                fotopath.setText(foto_name)
                imageFoto.setImageURI(image_uri)
                //imagenToUpload.uploadFoto(foto_full_path)
                //foto_name = "/media/" + foto_name
            }
        }
    }
    private fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e("e", "getRealPathFromURI Exception : $e")
            return ""
        } finally {
            cursor?.close()
        }
    }
}

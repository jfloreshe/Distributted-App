package com.example.consumewebservice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


//class MainActivity : AppCompatActivity(), FletchCompleteListener {
private const val PERMISSION_REQUEST_CODE = 1231

class MainActivity : AppCompatActivity() {
    private lateinit var chapterDetailsJSON: JSONObject
    var client = OkHttpClient()
    var request = OkHttpRequest(client)

    private lateinit var mensajeResponse:EditText //lateiniti permite incializar despues la variable
    private lateinit var idPost:EditText
    private lateinit var passwordPost:EditText

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!checkPermissions(permissions)) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }

        mensajeResponse = findViewById(R.id.editText_GET)
        idPost = findViewById(R.id.editText_id_user)
        passwordPost = findViewById(R.id.editText_password)
        chapterDetailsJSON = JSONObject()
        chapterDetailsJSON.put("valid",false)

        //run("http://192.168.1.60:8000/users/" )
    }
    private fun checkPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    Toast.makeText(applicationContext, "Permission is not granted.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val data = response.body()?.string() as String
                Log.e("OKHTTP:", data)
                setText(data)
            }
        })
    }
    fun confirmPass(view: View){
        //Cambiar el url correcto DEL NUEVO SERVIDOR
        val url = globalServer + "/confirmPass/"
        val map: HashMap<String,String> = hashMapOf("id_user" to idPost.text.toString(),"password" to passwordPost.text.toString())
        val intent = Intent(this,MenuOpciones::class.java)
        request.POST(url,map,object:Callback {
            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                runOnUiThread{
                    try{
                        var json = JSONObject(responseData)
                        println("request successfull")
                        //this@MainActivity.fetchComplete()
                        if(json.getString("id_user")=="True" && json.getString("password")=="True") {
                            setText("Usuario correcto")
                            startActivity(intent)
                        }
                        else
                            setText("Usuario no existe o password incorrecto")
                    }catch (e:JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Request failure.")
            }
        })
    }

    fun setText(data:String){
        runOnUiThread{
            mensajeResponse.setText(data)
        }
    }
}
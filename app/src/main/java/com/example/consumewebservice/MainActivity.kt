package com.example.consumewebservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

//class MainActivity : AppCompatActivity(), FletchCompleteListener {
class MainActivity : AppCompatActivity() {
    private lateinit var chapterDetailsJSON: JSONObject
    var client = OkHttpClient()
    var request = OkHttpRequest(client)

    private lateinit var mensajeResponse:EditText //lateiniti permite incializar despues la variable
    private lateinit var idPost:EditText
    private lateinit var passwordPost:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mensajeResponse = findViewById(R.id.editText_GET)
        idPost = findViewById(R.id.editText_id_user)
        passwordPost = findViewById(R.id.editText_password)
        chapterDetailsJSON = JSONObject()
        chapterDetailsJSON.put("valid",false)

        run("http://192.168.1.60:8000/users/" )
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
        val url = "http://192.168.1.60:8000/confirmPass/"
        val map: HashMap<String,String> = hashMapOf("id_user" to idPost.text.toString(),"password" to passwordPost.text.toString())

        request.POST(url,map,object:Callback {
            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                runOnUiThread{
                    try{
                        var json = JSONObject(responseData)
                        println("request successfull")
                        //this@MainActivity.fetchComplete()
                        if(json.getString("id_user")=="True" && json.getString("password")=="True")
                            setText("Usuario correcto")
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

    /*override fun fetchComplete() {
        println("fetchComplete:   " + chapterDetailsJSON)

        if(chapterDetailsJSON.getBoolean("valid")){
        }

    }*/
}
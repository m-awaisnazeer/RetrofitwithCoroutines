package com.fhatechnology.retrofitwithcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getComments()

            if (response.isSuccessful){
                for ((index,value) in response.body()!!.withIndex()){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@MainActivity, "value:${value.email}", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this@MainActivity, "value:${response.message()}", Toast.LENGTH_SHORT).show()

            }

        }
    }


}
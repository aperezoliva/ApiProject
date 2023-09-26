package com.example.apiproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apiproject.ui.theme.ApiProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    sendRequest()
                }
            }
        }
    }


    private fun sendRequest(
    ) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dummyjson.com")
            .build()
            .create(SteamApi::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object: Callback<List<ProfileModel>?> {
            override fun onResponse(call: Call<List<ProfileModel>?>, response: Response<List<ProfileModel>?>) {
                if(response.isSuccessful) {
                    val responseBody = response.body()!!

                    for (myData in responseBody) {
                        Log.d("Main", "success! " + myData.todo)
                    }

                }
            }

            override fun onFailure(call: Call<List<ProfileModel>?>, t: Throwable) {
                Log.d("Main",  t.message.toString())
            }
        })
    }
}

@Composable
fun Greeting(fName: String, lName: String, age: Int, email: String, modifier: Modifier = Modifier) {
    Text(
        text = "Name: $fName $lName, age: $age, email: $email",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ApiProjectTheme {
//        Greeting("Android")
//    }
//}
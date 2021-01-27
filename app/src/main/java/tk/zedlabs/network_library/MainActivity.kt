package tk.zedlabs.network_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.json.JSONObject
import tk.zedlabs.networking_lib.JSONObjectListener
import tk.zedlabs.networking_lib.SimpleRequest
import tk.zedlabs.networking_lib.SimpleRequest.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv_main)

       /*
       * Sample usecase to make {@GET} request and log the result
       */

        var result = ""
        SimpleRequest.Request(GET)
            .url("https://jsonplaceholder.typicode.com/posts/1")
            .makeRequest(object : JSONObjectListener {
                override fun onResponse(res: JSONObject?) {
                    result = res.toString()
                    Log.e("Launcher-Activity", "onResponse: success -> $result")

                }

                override fun onFailure(e: Exception?) {
                    result = e.toString()
                    Log.e("Launcher-Activity", "onResponse: failure -> $result")
                }
            })
    }
}
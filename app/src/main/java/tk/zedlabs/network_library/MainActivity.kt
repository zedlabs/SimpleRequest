package tk.zedlabs.network_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
       * Sample usecase to make {@GET} request and display
       * the result in a textView
       */

        SimpleRequest.Request(GET)
            .url("https://jsonplaceholder.typicode.com/posts/1")
            .makeRequest(object : JSONObjectListener {
                override fun onResponse(res: JSONObject?) {
                    tv.text = res.toString()
                }
                override fun onFailure(e: Exception?) {
                    tv.text = e.toString()
                }
            })
    }
}
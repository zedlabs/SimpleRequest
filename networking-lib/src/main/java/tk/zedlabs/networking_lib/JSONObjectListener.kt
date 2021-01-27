package tk.zedlabs.networking_lib

import org.json.JSONObject


interface JSONObjectListener {
    fun onResponse(res: JSONObject?)
    fun onFailure(e: Exception?)
}
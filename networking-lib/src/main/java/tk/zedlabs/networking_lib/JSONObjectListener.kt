package tk.zedlabs.networking_lib

import org.json.JSONObject

/**
    The response will be org.json.JSONObject
    and failure will be java.lang.Exception
**/

interface JSONObjectListener {
    fun onResponse(res: JSONObject?)
    fun onFailure(e: Exception?)
}
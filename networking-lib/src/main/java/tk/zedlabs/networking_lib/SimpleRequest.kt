package tk.zedlabs.networking_lib

import org.json.JSONObject
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets.*
import java.util.*

object SimpleRequest {

    const val GET = "GET"
    const val POST = "POST"
    const val DELETE = "DELETE"
    const val PUT = "PUT"

    class Request(internal val method: String) {
        /* internal because the getters are exposed */

        internal val header: MutableMap<String, String> = HashMap()
        internal var url: String? = null
        internal var body: ByteArray? = null

        private var jsonObjReqListener: JSONObjectListener? = null
        private var threadExecutor: ThreadExecutor = ThreadExecutor()

        fun url(url: String?): Request {
            this.url = url
            return this
        }

        fun body(bodyJson: JSONObject?): Request {
            val textBody = bodyJson?.toString()
            body = textBody?.toByteArray(charset(UTF_8.toString()))
            this.header["Content-Type"] = "application/json"
            return this
        }

        fun header(header: Map<String, String>?): Request {
            if (header.isNullOrEmpty()) return this
            this.header.putAll(header)
            return this
        }

        fun makeRequest(jsonObjectListener: JSONObjectListener?): Request {
            this.jsonObjReqListener = jsonObjectListener
            threadExecutor.execute(RequestTask(this))
            return this
        }

        internal fun sendResponse(resp: Response?, e: Exception?) {
            if (jsonObjReqListener != null) {
                if (e != null) jsonObjReqListener?.onFailure(e)
                else jsonObjReqListener?.onResponse(resp?.asJSONObject())
            }
        }
    }

    internal class RequestTask(private val req: Request) : Runnable {
        override fun run() {
            try {
                val conn = request()
                val parsedResponse = parseResponse(conn)
                req.sendResponse(parsedResponse, null)
            } catch (e: IOException) {
                req.sendResponse(null, e)
            }
        }

        @Throws(IOException::class)
        private fun request(): HttpURLConnection {
            val url = URL(req.url)
            val conn = url.openConnection() as HttpURLConnection
            val method = req.method
            conn.requestMethod = method
            for ((key, value) in req.header) {
                conn.setRequestProperty(key, value)
            }
            if (req.body != null) {
                val outputStream = conn.outputStream
                outputStream.write(req.body)
            }
            conn.connect()
            return conn
        }

        @Throws(IOException::class)
        private fun parseResponse(conn: HttpURLConnection): Response {
            try {
                val bos = ByteArrayOutputStream()
                val status = conn.responseCode
                val validStatus = status in 200..299
                val inpStream = if (validStatus) conn.inputStream else conn.errorStream
                var read: Int
                var totalRead = 0
                val buf = ByteArray(bufferSize)
                while (inpStream.read(buf).also { read = it } != -1) {
                    bos.write(buf, 0, read)
                    totalRead += read
                }
                return Response(bos.toByteArray())
            } finally {
                conn.disconnect()
            }
        }
    }

    class Response(private val data: ByteArray) {
        @Throws(JSONException::class)
        fun asJSONObject(): JSONObject {
            val str = String(data, UTF_8)
            return if (str.isEmpty()) JSONObject() else JSONObject(str)
        }
    }
}
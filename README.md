# Network-Library

#### A basic networking library implementation for android using Kotlin and Coroutines making use of the builder pattern

* Features
  - Requests (GET, POST, DELETE, PUT)
  - Headers
  - Body

## Sample Usecase
``` 
       /* Sample usecase to make {@GET} request and log the result */

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

```

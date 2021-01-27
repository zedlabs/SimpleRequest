package tk.zedlabs.networking_lib

import android.os.Handler
import android.os.HandlerThread
import android.os.Process.THREAD_PRIORITY_DEFAULT

class ThreadExecutor {
    fun execute(runnable: Runnable?) {
        /* change this to kotlin coroutines */

        val handlerThread = HandlerThread(
            "PriorityHandlerThread",
            THREAD_PRIORITY_DEFAULT
        )
        val handler = Handler(handlerThread.looper)
        handler.post(runnable!!)
    }
}
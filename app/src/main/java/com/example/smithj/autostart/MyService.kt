package com.example.smithj.autostart

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v4.app.JobIntentService
import android.os.SystemClock
import android.util.Log
import android.widget.Toast




class MyService: JobIntentService() {

    companion object {
        private const val JOB_ID: Int = 0x01

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyService::class.java, JOB_ID, work)
        }
    }


    override fun onHandleWork(intent: Intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        Log.i("SimpleJobIntentService", "Executing work: $intent")
        var label = intent.getStringExtra("label")
        if (label == null) {
            label = intent.toString()
        }
        toast("Executing: $label")
        for (i in 0..4) {
            Log.i("SimpleJobIntentService", "Running service " + (i + 1)
                    + "/5 @ " + SystemClock.elapsedRealtime())
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
            }
        }
        Log.i("SimpleJobIntentService", "Completed service @ " + SystemClock.elapsedRealtime())
    }


    override fun onDestroy() {
        super.onDestroy()
        toast("All work complete")
    }

    val mHandler = Handler()

    // Helper for showing tests
    fun toast(text: CharSequence) {
        mHandler.post({ Toast.makeText(this@MyService, text, Toast.LENGTH_SHORT).show() })
    }
}
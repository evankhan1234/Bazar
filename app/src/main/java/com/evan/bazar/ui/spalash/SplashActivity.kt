package com.evan.bazar.ui.spalash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.evan.bazar.R
import com.evan.bazar.ui.auth.LoginActivity
import com.evan.bazar.ui.home.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                Log.e("Device_Token", token)

            })
        Handler().postDelayed({
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }

        }, 300)
    }
}

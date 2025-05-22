package com.rezakur.storify.presentation.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rezakur.storify.R
import com.rezakur.storify.presentation.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        lifecycleScope.launch {
            delay(2000L)
            startActivity(Intent(this@LauncherActivity, LoginActivity::class.java))
        }
    }
}
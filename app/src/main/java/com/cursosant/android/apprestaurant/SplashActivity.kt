package com.cursosant.android.apprestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.cursosant.android.apprestaurant.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashBinding
    val delaey:Long = 6000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        cambiarActivity()
    }

    private fun cambiarActivity() {
        Handler(this.mainLooper).postDelayed({
            val intent = Intent(this@SplashActivity,Login::class.java)
            startActivity(intent)
            finish()
        },delaey)
    }
}
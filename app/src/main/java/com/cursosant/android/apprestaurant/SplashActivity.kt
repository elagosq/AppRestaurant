package com.cursosant.android.apprestaurant


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.cursosant.android.apprestaurant.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashBinding
    val DURACION:Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        cambiarActivity()
    }

    private fun cambiarActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity,Login::class.java)
            startActivity(intent)
            finish()
        },DURACION)
    }
}
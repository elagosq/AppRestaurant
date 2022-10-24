package com.cursosant.android.apprestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import com.cursosant.android.apprestaurant.databinding.ActivityLoginBinding
import roomDatabase.RestaurantDatabase

class Login : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnSesion.setOnClickListener {
            validateForm()
        }

        mBinding.btnRegister.setOnClickListener {
            val intent = Intent(this@Login, RegistroUsuario::class.java)
            startActivity(intent)
        }

    }


    fun validateForm(){
        val til_email = mBinding.tilUserEmail
        val til_pass  = mBinding.tilUserPass

        val email =  mBinding.etUserEmail.text.toString().trim()
        val pass  = mBinding.etUserPass.text.toString().trim()

        val validate = Validate()
        val emailVal = validate.validarEmail(email)
        val passVal = validate.validatePassword(pass)


        if(emailVal != null) til_email.error = emailVal else til_email.error = ""

        if(passVal != null){
            til_pass.error = passVal
            return
        } else {
            til_pass.error = ""
            cleanForm()
        }

        lifecycleScope.launch{
            val response = RestaurantApplication.database.userDao().login(email,pass)

            if(response.size == 1){
               val intent = Intent(this@Login,MainActivity::class.java)
               startActivity(intent)
            }else{
                til_email.error = "Usuario o contraseña invalido"
                til_pass.error = "Usuario o contraseña invalido"
                Toast.makeText(this@Login,"Usuario no registrado o incorrecto",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanForm(){
        mBinding.etUserEmail.setText("")
        mBinding.etUserPass.setText("")
    }
}


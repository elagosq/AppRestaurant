package com.cursosant.android.apprestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.cursosant.android.apprestaurant.databinding.ActivityRegistrousuarioBinding
import kotlinx.coroutines.launch
import roomDatabase.RestaurantDatabase
import roomDatabase.entity.UserEntity

class RegistroUsuario : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegistrousuarioBinding
    private var mUserEntity: UserEntity?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegistrousuarioBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        getSupportActionBar()?.setTitle("");

        mUserEntity = UserEntity(email = "", password = "")

        mBinding.btnRegister.setOnClickListener {
            validateForm()
        }
    }

    fun validateForm(){
        val til_email = mBinding.tilUserEmail
        val til_pass  = mBinding.tilUserPass

        val emailuser =  mBinding.etUserEmail.text.toString().trim()
        val passuser  =  mBinding.etUserPass.text.toString().trim()

        val validate = Validate()
        val emailVal = validate.validarEmail(emailuser)
        val passVal = validate.validatePassword(passuser)

        if(emailVal != null) til_email.error = emailVal else til_email.error = ""

        if(passVal != null){
            til_pass.error = passVal
            return
        } else {
            til_pass.error = ""
            cleanForm()
        }

        with(mUserEntity!!){
            email    = emailuser
            password = passuser
        }

        lifecycleScope.launch {
            val user = RestaurantApplication.database.userDao().validateUser(emailuser)
            Log.d("user","$user")
            if(user.isEmpty()){
                //room.userDao().addUser(mUserEntity!!)
                RestaurantApplication.database.userDao().addUser(mUserEntity!!)
                Toast.makeText(this@RegistroUsuario,"Usuario Registrado Correctamente",Toast.LENGTH_LONG).show()
            }else{
                mBinding.tilUserEmail.error = "Email ya existe"
                mBinding.tilUserPass.error = "password"
                Toast.makeText(this@RegistroUsuario,"Usuario ya existe",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanForm(){
        mBinding.etUserEmail.setText("")
        mBinding.etUserPass.setText("")
    }
}
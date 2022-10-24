package com.cursosant.android.apprestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.cursosant.android.apprestaurant.databinding.ActivityFormRestauranteBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import roomDatabase.entity.RestaurantEntity

class FormRestaurante : AppCompatActivity() {

    private lateinit var mBinding: ActivityFormRestauranteBinding
    private var mIsEditMode: Boolean = false
    private var mRestaurantEntity: RestaurantEntity?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFormRestauranteBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val intent = getIntent()
        val id = intent.getLongExtra("id",0)


        if(id > 0){
            mIsEditMode = true
            getRestaurant(id)

        }else{
            mIsEditMode = false
            mRestaurantEntity = RestaurantEntity(name = "", direction = "", phone = "",website = "",urlImg="",description="")
        }

        setSupportActionBar(mBinding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setTitle(if(mIsEditMode) "Editar Restaurante" else "Crear Restaurante");

        getSupportActionBar()?.setCustomView(R.layout.personalizado_logo)
        getSupportActionBar()?.setDisplayUseLogoEnabled(true)

        mBinding.btnRestSave.setText(if(mIsEditMode) "Editar" else "Crear")

        mBinding.btnRestSave.setOnClickListener {
            FormSave()
        }
    }

    private fun getRestaurant(id: Long) {
         lifecycleScope.launch {
             mRestaurantEntity = RestaurantApplication.database.restaurantDao().getRestaurantById(id)
             if(mRestaurantEntity != null) setUiStart(mRestaurantEntity!!)
         }
    }

    private fun setUiStart(restaurant: RestaurantEntity) {
        with(mBinding){
            etNombRestaurant.text = restaurant.name.editable()
            etDirRestaurant.text = restaurant.direction.editable()
            etTelRestaurant.text = restaurant.phone.editable()
            etWebRestaurant.text = restaurant.website.editable()
            etPhotoUrl.text = restaurant.urlImg.editable()
            etDespRest.text = restaurant.description.editable()
        }
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun FormSave() {

         val til_name_restaurant = mBinding.tilNombRestaurant
         val til_dir_restaurant =  mBinding.tilDirRestaurant
         val til_tel_restaurant =  mBinding.tilTelRestaurant
         val til_web_restaurant =  mBinding.tilWebRestaurant
         val til_photourl_restaurant = mBinding.tilPhotoUrl
         val til_desp_rest = mBinding.tilDespRest

         val nameRest  = mBinding.etNombRestaurant.text.toString().trim()
         val dirRest   = mBinding.etDirRestaurant.text.toString().trim()
         val telRest   = mBinding.etTelRestaurant.text.toString().trim()
         val webRest   = mBinding.etWebRestaurant.text.toString().trim()
         val phurlRest = mBinding.etPhotoUrl.text.toString().trim()
         val despRest  = mBinding.etDespRest.text.toString().trim()

         val validate = Validate()
         val valNameRest = validate.validateInputNulo(nameRest)
         val valDirRest = validate.validateInputNulo(dirRest)
         val valTelRest = validate.validatePhone(telRest)
         val valWebRest = validate.validateWeb(webRest)
         val valurlPhotoRest = validate.validateWeb(phurlRest)
         val valDespRest = validate.validateInputNulo(despRest)

         if(valNameRest) til_name_restaurant.error = "Campo es obligatorio" else til_name_restaurant.error = ""
         if(valDirRest) til_dir_restaurant.error = "Campo es obligatorio" else til_dir_restaurant.error = ""

         if(valTelRest != null){
             til_tel_restaurant.error = valTelRest
         } else {
             til_tel_restaurant.error = ""
         }

         if(valurlPhotoRest != null){
             til_photourl_restaurant.error = valurlPhotoRest
         }else{
             til_photourl_restaurant.error = ""
         }

         if(valWebRest != null){
             til_web_restaurant.error = valWebRest

         } else {
             til_web_restaurant.error = ""
         }

         if(valDespRest){
             til_desp_rest.error = "Campo es obligatorio"
             return
         } else {
             til_desp_rest.error = ""
             cleanForm()
         }

        if(mRestaurantEntity != null) {
            with(mRestaurantEntity!!){
                name = nameRest
                direction = dirRest
                phone = telRest
                website = webRest
                urlImg = phurlRest
                description = despRest
            }

            lifecycleScope.launch {
                 if (mIsEditMode) RestaurantApplication.database.restaurantDao().updateRestaurant(mRestaurantEntity!!)
                 else mRestaurantEntity!!.id = RestaurantApplication.database.restaurantDao().addRestaurant(mRestaurantEntity!!)
                if(mIsEditMode){
                    //mActivity?.updateRestaurant(mRestaurantEntity!!)
                    Toast.makeText(this@FormRestaurante,R.string.edit_restaurant_message_update_success,Toast.LENGTH_LONG).show()
                    delay(2000L)
                    super.onBackPressed()
                }else{
                    //mActivity?.addRestaurant(mRestaurantEntity!!)
                    Toast.makeText(this@FormRestaurante,R.string.add_restaurant_message_save_success,Toast.LENGTH_LONG).show()
                    delay(2000L)
                    super.onBackPressed()
                }
            }
        }
    }
    fun cleanForm(){
        mBinding.etNombRestaurant.setText("")
        mBinding.etDirRestaurant.setText("")
        mBinding.etTelRestaurant.setText("")
        mBinding.etWebRestaurant.setText("")
        mBinding.etPhotoUrl.setText("")
        mBinding.etDespRest.setText("")
    }
}
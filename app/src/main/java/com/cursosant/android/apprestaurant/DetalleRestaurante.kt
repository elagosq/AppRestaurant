package com.cursosant.android.apprestaurant

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.cursosant.android.apprestaurant.databinding.ActivityDetalleRestauranteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetalleRestaurante : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var mBinding: ActivityDetalleRestauranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetalleRestauranteBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val intent = getIntent()
        val titleRestaurant = intent.getStringExtra("name").toString();
        val phoneRestaurant = intent.getStringExtra("phone").toString()
        val webRestaurant   = intent.getStringExtra("website").toString()
        val dirRestaurant   = intent.getStringExtra("direction").toString()
        val despRestaurant  = intent.getStringExtra("description").toString()
        val checked = intent.getBooleanExtra("favorite",true)
        Log.i("checked","$checked")

        setSupportActionBar(mBinding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setTitle(titleRestaurant);

        mBinding.tvDirection.setText(dirRestaurant)
        mBinding.tvTel.setText(phoneRestaurant)
        mBinding.tvWeb.setText(webRestaurant)
        mBinding.tvDescription.setText(despRestaurant)
        mBinding.cbFavorite.isChecked = checked

        mBinding.btnMenu.setOnClickListener {
            alertShowOption(phoneRestaurant,webRestaurant)
        }
     }

    private fun alertShowOption(phone:String,website:String) {
        val items = resources.getStringArray(R.array.array_options_item)

        MaterialAlertDialogBuilder(this)
            .setTitle("Menu de Opciones")
            .setItems(items,{ dialogInterface,i ->
               when(i){
                   0 ->  GlobalScope.launch{ dial(phone) }
                   1 ->  GlobalScope.launch{ goToWebSite(website) }
               }
            })
            .setPositiveButton("Cerrar",null)
            .show()
    }


    private suspend fun dial(phone : String){
        Log.i("Phone",phone)


            val callIntent = Intent().apply{
                action = Intent.ACTION_DIAL
                data = Uri.parse("tel:$phone")
            }

            startIntent(callIntent)

    }

    private suspend fun goToWebSite(website : String){
         Log.i("website",website)

         val websiteIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(website)
         }
        startIntent(websiteIntent)
    }


    private fun startIntent(intent: Intent){
        if(intent.resolveActivity(packageManager) != null)  startActivity(intent)
        else Toast.makeText(this,R.string.main_error_no_resolve,Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}
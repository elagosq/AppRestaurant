package com.cursosant.android.apprestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.apprestaurant.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import roomDatabase.entity.RestaurantEntity

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mAdapter: RestaurantAdapter
    private lateinit var mGridLayout: GridLayoutManager
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()

        binding.fab.setOnClickListener {
           val intent = Intent(this@MainActivity, FormRestaurante::class.java)
           startActivity(intent)
       }
    }

    private fun setupRecyclerView() {
        mAdapter = RestaurantAdapter(mutableListOf(),this)
        mGridLayout = GridLayoutManager(this,resources.getInteger(R.integer.main_columns))
        getRestaurants()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun getRestaurants() {
        lifecycleScope.launch {
           val restaurants = RestaurantApplication.database.restaurantDao().getAllRestaurant()
           mAdapter.setRestaurants(restaurants)
       }
    }

    /*
    * OnClickListener
    * */
    //Metodo onClick envia el objecto restaurant a la vista detalleProducto
    override fun onClick(restaurant: RestaurantEntity) {
         val intent = Intent(this@MainActivity,DetalleRestaurante::class.java)
        intent.putExtra("name",restaurant.name)
        intent.putExtra("direction",restaurant.direction)
        intent.putExtra("phone",restaurant.phone)
        intent.putExtra("website",restaurant.website)
        intent.putExtra("urlImg",restaurant.urlImg)
        intent.putExtra("description", restaurant.description)
        intent.putExtra("favorite",restaurant.favorite)
        startActivity(intent)
    }

    override fun onFavorite(restaurant: RestaurantEntity) {
        restaurant.favorite = !restaurant.favorite
        lifecycleScope.launch {
            RestaurantApplication.database.restaurantDao().updateRestaurant(restaurant)
            mAdapter.update(restaurant)
        }
    }


    override fun onEdit(restaurant: RestaurantEntity) {
        val intent = Intent(this@MainActivity,FormRestaurante::class.java)
        intent.putExtra("id",restaurant.id)
        startActivity(intent)
    }


    override fun onDelete(restaurant: RestaurantEntity) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_confirm, { _, _ ->
                lifecycleScope.launch {
                    RestaurantApplication.database.restaurantDao().deleteRestaurant(restaurant)
                    mAdapter.delete(restaurant)
                }
            })
            .setNegativeButton(R.string.dialog_delete_cancel,null)
            .show()
     }


    override fun onResume() {
        super.onResume()
        Log.i("Resume","onResumen")
        getRestaurants()
    }

}


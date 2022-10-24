package com.cursosant.android.apprestaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cursosant.android.apprestaurant.databinding.ItemRestaurantBinding
import roomDatabase.entity.RestaurantEntity

class RestaurantAdapter (private var restaurants: MutableList<RestaurantEntity>,private var listener:OnClickListener) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant,parent,false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val restaurant = restaurants.get(position)

        with(holder){
            setListener(restaurant)
            binding.tvName.text = restaurant.name
            binding.cbFavorite.isChecked = restaurant.favorite
            Glide.with(mContext)
                .load(restaurant.urlImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }

    override fun getItemCount(): Int  = restaurants.size

    fun setRestaurants(restaurants: MutableList<RestaurantEntity>){
        this.restaurants = restaurants
        notifyDataSetChanged()
    }


    fun update(restaurant: RestaurantEntity){
       val index = restaurants.indexOf(restaurant)

        if(index != -1){
            restaurants.set(index,restaurant)
            notifyItemChanged(index)
        }
    }

    fun delete(restaurant: RestaurantEntity){
        val index = restaurants.indexOf(restaurant)
        if(index != -1){
            restaurants.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var binding = ItemRestaurantBinding.bind(view)


        fun setListener(restaurant: RestaurantEntity){

            with(binding.root){
                setOnClickListener { listener.onClick(restaurant) }
                binding.btnEdit.setOnClickListener {
                        listener.onEdit(restaurant)
                }

                binding.btnDelete.setOnClickListener {
                    listener.onDelete(restaurant)
                }

            }
            binding.cbFavorite.setOnClickListener {
                listener.onFavorite(restaurant)
            }
        }
      }
    }
package com.cursosant.android.apprestaurant

import roomDatabase.entity.RestaurantEntity

interface OnClickListener {
    fun onClick(restaurantEntity: RestaurantEntity)
    fun onFavorite(restaurantEntity: RestaurantEntity)
    fun onEdit(restaurantEntity: RestaurantEntity)
    fun onDelete(restaurantEntity: RestaurantEntity)
}
package roomDatabase.dao

import androidx.room.*
import roomDatabase.entity.RestaurantEntity

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM Restaurant")

    suspend fun getAllRestaurant(): MutableList<RestaurantEntity>

    @Query("SELECT * FROM Restaurant where id = :id")

    suspend fun getRestaurantById(id: Long): RestaurantEntity

    @Insert
    suspend fun addRestaurant(restaurantEntity: RestaurantEntity): Long

    @Update
    suspend fun updateRestaurant(restaurantEntity: RestaurantEntity)

    @Delete
    suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity)
}
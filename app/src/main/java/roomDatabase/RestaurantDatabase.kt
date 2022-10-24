package roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import roomDatabase.dao.RestaurantDao
import roomDatabase.dao.UserDao
import roomDatabase.entity.RestaurantEntity
import roomDatabase.entity.UserEntity

@Database(entities = arrayOf(UserEntity::class,RestaurantEntity::class), version = 2)
abstract class RestaurantDatabase:RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun restaurantDao(): RestaurantDao
}
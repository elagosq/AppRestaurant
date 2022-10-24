package roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import roomDatabase.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE email=:email")
    suspend fun validateUser(email : String):List<UserEntity>

    @Query("SELECT * FROM User WHERE email=:email AND password=:password")
    suspend fun login(email: String, password: String): List<UserEntity>

    @Insert
    suspend fun addUser(usuario: UserEntity):Long
}
package roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
       @PrimaryKey(autoGenerate = true) var id: Long = 0,
       var email:String?=null,
       var password:String?=null
)
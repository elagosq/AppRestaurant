package roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurant")
data class RestaurantEntity(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                        var name: String,
                        var direction: String,
                        var phone:String = "",
                        var website: String = "",
                        var urlImg: String,
                        var description : String = "",
                        var favorite: Boolean = false)

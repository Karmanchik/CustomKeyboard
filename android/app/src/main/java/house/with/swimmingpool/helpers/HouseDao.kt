package house.with.swimmingpool.helpers

import androidx.room.*
import house.with.swimmingpool.models.HouseCatalogData

@Dao
interface HouseDao {
    @Query("SELECT * FROM house")
    fun getAll(): List<HouseCatalogData>

    @Query("SELECT * FROM house WHERE id = :id")
    fun getById(id: Int): HouseCatalogData?

    @Insert
    fun insert(house: HouseCatalogData)

    @Update
    fun update(house: HouseCatalogData)

    @Delete
    fun delete(house: HouseCatalogData)
}
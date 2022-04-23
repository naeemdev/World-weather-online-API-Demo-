package com.naeemdev.worldweatheronline.data.local


import androidx.room.*
import com.naeemdev.worldweatheronline.model.CurrentConditionEntityModel

@Dao
interface WeatherDao {

    @Query("SELECT * FROM CurrentConditionEntityModel order by `type` DESC")
    fun getAll(): List<CurrentConditionEntityModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currentConditionEntities: List<CurrentConditionEntityModel>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentConditionEntities: CurrentConditionEntityModel)


    @Query("SELECT * FROM CurrentConditionEntityModel where `type` =:query")
    fun getDetail(query:String): List<CurrentConditionEntityModel>?

    @Query("Delete FROM CurrentConditionEntityModel where `type` =:query")
    fun deleteSingleItem(query:String)


    @Delete
    fun deleteAll(currentConditionEntity: List<CurrentConditionEntityModel>)

    @Query("DELETE FROM CurrentConditionEntityModel")
    fun deleteAllData()
}
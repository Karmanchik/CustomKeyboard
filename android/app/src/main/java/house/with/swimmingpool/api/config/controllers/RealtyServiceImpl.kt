package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.config.interfaces.IRealtyService
import house.with.swimmingpool.api.retrofit.IRealty
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.HouseCatalog
import house.with.swimmingpool.models.HouseExample
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RealtyServiceImpl: IRealtyService {

    override fun getHouseCatalog(){
        getRetrofit().create(IRealty :: class.java)
            .getHouses()
            .enqueue(object : Callback<HouseCatalog> {
                override fun onResponse(call: Call<HouseCatalog>, response: Response<HouseCatalog>) {
                    Log.e("Done", "response.body()!!.apiVersion")
                }

                override fun onFailure(call: Call<HouseCatalog>, t: Throwable) {
                    Log.e("Done", "Error", t)
                }
            })
    }

    override fun getHouseExample(){
        getRetrofit().create(IRealty :: class.java)
            .getHousesExample()
            .enqueue(object : Callback<HouseExample> {
                override fun onResponse(call: Call<HouseExample>, response: Response<HouseExample>) {
                    Log.e("Done", "response.body()!!.apiVersion")
                }

                override fun onFailure(call: Call<HouseExample>, t: Throwable) {
                    Log.e("Done", "Error", t)
                }
            })
    }
}
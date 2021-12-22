package com.bell.calorieda.ui.detailNutrient

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bell.calorieda.data.api.ApiConfig
import com.bell.calorieda.data.response.FoodsItem
import com.bell.calorieda.data.response.Response
import com.bell.calorieda.ui.calculate.CalculateActivity
import retrofit2.Call
import retrofit2.Callback

class NutrientDetailViewModel : ViewModel() {
    var listFoods = MutableLiveData<ArrayList<FoodsItem>?>()

    fun setSearchFood(query : String?){
        val client = ApiConfig.getApiService().postQuery(query)
        client.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        listFoods.postValue(responseBody.foods!!)
                    }
                }else{
                    listFoods.postValue(null)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d(CalculateActivity::class.java.simpleName, "Failure : ${t.message}")
            }

        })
    }

    fun getFoods(): MutableLiveData<ArrayList<FoodsItem>?> {
        return listFoods
    }
}
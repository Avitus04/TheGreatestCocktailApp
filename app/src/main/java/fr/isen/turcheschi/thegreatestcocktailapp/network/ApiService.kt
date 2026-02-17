package fr.isen.turcheschi.thegreatestcocktailapp.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

import okhttp3.ResponseBody

import retrofit2.Call

interface ApiService {

    @GET("random.php")
    fun getRandom(): Call<ResponseBody>

    @GET("list.php?c=list")
    fun getCategories(): Call<ResponseBody>

    @GET("filter.php")
    fun getDrinksByCategory(
        @Query("c") category: String
    ): Call<ResponseBody>

    @GET("lookup.php")
    fun getDrinkDetails(
        @Query("i") id: String
    ): Call<ResponseBody>
}

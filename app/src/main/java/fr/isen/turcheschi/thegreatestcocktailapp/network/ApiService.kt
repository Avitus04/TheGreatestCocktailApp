package fr.isen.turcheschi.thegreatestcocktailapp.network

import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

import okhttp3.ResponseBody

import retrofit2.Call

interface ApiService {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("list.php?c=list")
    fun getCategories(): Call<CategoryListResponse>

    @GET("filter.php")
    fun getDrinksPreview(
        @Query("c") category: String
    ): Call<DrinkFilterResponse>

    @GET("lookup.php")
    fun getDetailCocktail(
        @Query("i") id: String
    ): Call<CocktailResponse>
}

package fr.isen.turcheschi.thegreatestcocktailapp.network

import com.google.gson.Gson
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkCategory
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NetworkManager {

    private const val BASE_URL =
        "https://www.thecocktaildb.com/api/json/v1/1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
    }

    private val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    private val gson = Gson()

    fun getRandomCocktail(
        onSuccess: (CocktailResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getRandom().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                try {
                    val json = response.body()?.string()
                    val result = gson.fromJson(
                        json,
                        CocktailResponse::class.java
                    )
                    onSuccess(result)
                } catch (e: Exception) {
                    onError(e)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getCategories(
        onSuccess: (CategoryListResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getCategories().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                try {
                    val json = response.body()?.string()
                    val result = gson.fromJson(
                        json,
                        CategoryListResponse::class.java
                    )
                    onSuccess(result)
                } catch (e: Exception) {
                    onError(e)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getDrinksByCategory(
        category: String,
        onSuccess: (DrinkFilterResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getDrinksByCategory(category)
            .enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    try {
                        val json = response.body()?.string()
                        val result = gson.fromJson(
                            json,
                            DrinkFilterResponse::class.java
                        )
                        onSuccess(result)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onError(t)
                }
            })
    }

    fun getDrinkDetails(
        id: String,
        onSuccess: (CocktailResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getDrinkDetails(id)
            .enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    try {
                        val json = response.body()?.string()
                        val result = gson.fromJson(
                            json,
                            CocktailResponse::class.java
                        )
                        onSuccess(result)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onError(t)
                }
            })
    }
}

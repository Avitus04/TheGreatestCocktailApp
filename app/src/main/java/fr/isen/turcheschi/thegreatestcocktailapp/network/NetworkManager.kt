package fr.isen.turcheschi.thegreatestcocktailapp.network

import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val BASE_URL =
        "https://www.thecocktaildb.com/api/json/v1/1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // 🔥 IMPORTANT
            .build()
    }

    private val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getRandomCocktail(
        onSuccess: (CocktailResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getRandomCocktail()
            .enqueue(object : Callback<CocktailResponse> {

                override fun onResponse(
                    call: Call<CocktailResponse>,
                    response: Response<CocktailResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onError(Throwable("Empty response"))
                    }
                }

                override fun onFailure(
                    call: Call<CocktailResponse>,
                    t: Throwable
                ) {
                    onError(t)
                }
            })
    }

    fun getCategories(
        onSuccess: (CategoryListResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getCategories()
            .enqueue(object : Callback<CategoryListResponse> {

                override fun onResponse(
                    call: Call<CategoryListResponse>,
                    response: Response<CategoryListResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onError(Throwable("Empty response"))
                    }
                }

                override fun onFailure(
                    call: Call<CategoryListResponse>,
                    t: Throwable
                ) {
                    onError(t)
                }
            })
    }

    fun getDrinksByCategory(
        category: String,
        onSuccess: (DrinkFilterResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getDrinksPreview(category)
            .enqueue(object : Callback<DrinkFilterResponse> {

                override fun onResponse(
                    call: Call<DrinkFilterResponse>,
                    response: Response<DrinkFilterResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onError(Throwable("Empty response"))
                    }
                }

                override fun onFailure(
                    call: Call<DrinkFilterResponse>,
                    t: Throwable
                ) {
                    onError(t)
                }
            })
    }

    fun getDrinkDetails(
        id: String,
        onSuccess: (CocktailResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getDetailCocktail(id)
            .enqueue(object : Callback<CocktailResponse> {

                override fun onResponse(
                    call: Call<CocktailResponse>,
                    response: Response<CocktailResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onError(Throwable("Empty response"))
                    }
                }

                override fun onFailure(
                    call: Call<CocktailResponse>,
                    t: Throwable
                ) {
                    onError(t)
                }
            })
    }
}
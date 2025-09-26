package fr.dawanAndroidMap.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Fournit une instance de Retrofit
 * pour interroger l'API Dawan.
 */
object Api {
    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dawan.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
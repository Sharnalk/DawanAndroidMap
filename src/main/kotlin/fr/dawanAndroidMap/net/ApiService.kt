package fr.dawanAndroidMap.net

import fr.dawanAndroidMap.data.Center
import retrofit2.http.GET

/**
 * Interface Retrofit pour interroger l'API REST de Dawan.
 */
interface ApiService {

    /**
     * Récupère la liste des centres Dawan depuis l’API.
     * @return Liste de centres de formation.
     */
    @GET("public/location/")
    suspend fun getCenters(): List<Center>
}
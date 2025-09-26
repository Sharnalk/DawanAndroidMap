package fr.dawanAndroidMap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente un centre de formation Dawan.
 *
 * Cette classe sert à deux choses :
 * - Room (SQLite) : annotation @Entity pour stocker les données localement.
 * - Gson (Retrofit) : annotation @field:SerializedName pour mapper les champs JSON.
 *
 * @property id Identifiant interne (auto-généré par Room).
 * @property name Nom du centre (vient du JSON : "name").
 * @property address Adresse complète du centre (vient du JSON : "address").
 * @property latitude Latitude du centre (vient du JSON : "latitude").
 * @property longitude Longitude du centre (vient du JSON : "longitude").
 */
@Entity(tableName = "centers")
data class Center(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
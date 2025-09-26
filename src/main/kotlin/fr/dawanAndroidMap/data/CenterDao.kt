package fr.dawanAndroidMap.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) pour la table "centers".
 * Fournit les opérations CRUD utilisées par Room pour accéder à la base.
 */
@Dao
interface CenterDao {

    /**
     * Récupère tous les centres de formation, triés par nom.
     *
     * @return un flux réactif (Flow) de liste de centres.
     */
    @Query("SELECT * FROM centers ORDER BY name")
    fun getAll(): Flow<List<Center>>

    @Query("SELECT * FROM centers ORDER BY name")
    suspend fun getAllOnce(): List<Center>

    @Query("SELECT COUNT(*) FROM centers")
    suspend fun count(): Int
    /**
     * Insère une liste de centres dans la base.
     * Si un centre existe déjà, il est remplacé.
     *
     * @param items la liste de centres à insérer.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Center>)

    /**
     * Supprime tous les centres de la base.
     */
    @Query("DELETE FROM centers")
    suspend fun clear()
}
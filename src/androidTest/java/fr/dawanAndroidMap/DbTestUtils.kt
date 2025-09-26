package fr.dawanAndroidMap

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import fr.dawanAndroidMap.data.AppDatabase
import fr.dawanAndroidMap.data.Center
import kotlinx.coroutines.runBlocking

/**
 * Utilitaires pour manipuler la base Room pendant les tests instrumentés.
 * On seed/clear la table "centers" pour stabiliser l'UI.
 */
object DbTestUtils {

    /** Vide la table et insère les centres fournis. */
    fun seedCenters(vararg centers: Center) {
        val ctx: Context = ApplicationProvider.getApplicationContext()
        val dao = AppDatabase.get(ctx).centerDao()
        runBlocking {
            dao.clear()
            dao.insertAll(centers.toList())
        }
    }

    /** Vide la table "centers". */
    fun clearCenters() {
        val ctx: Context = ApplicationProvider.getApplicationContext()
        val dao = AppDatabase.get(ctx).centerDao()
        runBlocking { dao.clear() }
    }
}

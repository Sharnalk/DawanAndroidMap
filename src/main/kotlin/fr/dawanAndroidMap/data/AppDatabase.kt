package fr.dawanAndroidMap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de données Room de l'application.
 * Elle contient une seule table : "centers".
 */
@Database(entities = [Center::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Retourne le DAO de la table des centres.
     */
    abstract fun centerDao(): CenterDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        /**
         * Retourne une instance unique (singleton) de la base.
         *
         * @param context Contexte Android requis pour accéder au système de fichiers.
         * @return Instance de [AppDatabase].
         */
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dawan-centers-db"
                ).build().also { INSTANCE = it }
            }
    }
}
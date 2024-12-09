package edu.itvo.pets.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.itvo.pets.core.utils.Converters
import edu.itvo.pets.data.local.daos.PetDao
import edu.itvo.pets.data.local.entities.PetEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Database(entities = [PetEntity::class,  ], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PetDB : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: PetDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PetDB {
            //--- Ejecutar si la instancia no es nulo y devolver la instancia,
            //--- sino crear la base de datos
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetDB::class.java,
                    "pets.dbf"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(PetDBCallback(scope))
                    .build()
                INSTANCE = instance
                //-- Devolver la instancia
                instance
            }
        }

        private class PetDBCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //--- Si se desea conservar los datos mediante reinicios de la aplicación,
                //--- comentar las siguientes líneas.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populate(database.petDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more elements
         */
        suspend fun populate(petDao: PetDao) {

            petDao.deleteAll()

            val pet = PetEntity(
                id = 1,
                name = "Firulais",
                birthdate = LocalDateTime.now().toString().substring(0,10),
                description = "La mascota favorita. Precargado en la App",
                race = "Labrador",
                image = "data:image/jpeg;base64,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"
            )
            petDao.insert(pet)

        }
    }
}
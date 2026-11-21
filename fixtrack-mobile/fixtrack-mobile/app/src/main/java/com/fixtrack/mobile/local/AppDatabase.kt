package com.fixtrack.mobile.local

import android.content.Context
import androidx.room.*

// 1. Entidad Unificada y Robusta para el SAT
@Entity(tableName = "partes_trabajo")
data class ParteEntity(
    @PrimaryKey(autoGenerate = false)
    val idParte: Long,
    val descripcionAveria: String,
    val estado: String,         // Ej: PENDIENTE, EN_PROCESO, RESUELTO
    val clienteNombre: String,
    val fecha: String,
    val sincronizado: Boolean = true // False si se creó offline y falta subir al servidor
)

// 2. DAO (Data Access Object) con operaciones asíncronas (suspend)
@Dao
interface ParteDao {
    @Query("SELECT * FROM partes_trabajo ORDER BY idParte DESC")
    suspend fun obtenerTodos(): List<ParteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(partes: List<ParteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUno(parte: ParteEntity)

    @Query("SELECT * FROM partes_trabajo WHERE sincronizado = 0")
    suspend fun obtenerNoSincronizados(): List<ParteEntity>
}

// 3. Base de Datos Singleton
@Database(entities = [ParteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parteDao(): ParteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fixtrack_offline.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
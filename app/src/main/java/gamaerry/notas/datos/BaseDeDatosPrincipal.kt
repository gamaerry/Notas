package gamaerry.notas.datos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Nota::class], version = 1)
abstract class BaseDeDatosPrincipal: RoomDatabase(){
    abstract fun getNotaDao(): NotaDao
    companion object {
        const val NOMBRE_BASE_DE_DATOS = "notas"
    }
}
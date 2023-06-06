package gamaerry.notas.datos

import androidx.room.Database
import androidx.room.RoomDatabase
import gamaerry.notas.modelo.Nota

// definicion de la base de datos:
// especifica el tipo de entidad
// así como su version y el nombre,
// y la funcion (abstracta) que permite
// la creacion del objeto Dao
// que terminara usando el Repositorio
@Database(entities = [Nota::class], version = 1)
abstract class BaseDeDatosPrincipal : RoomDatabase() {
    abstract fun getNotaDao(): DaoPrincipal

    companion object {
        const val NOMBRE_BASE_DE_DATOS = "notas"
    }
}
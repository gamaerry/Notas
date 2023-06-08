package gamaerry.notas.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import gamaerry.notas.modelo.Nota
import kotlinx.coroutines.flow.MutableStateFlow

// interfaz que define las interacciones (operaciones)
// necesarias con la base de datos de la libreria de Room
@Dao
interface DaoPrincipal {
    // insertar una nota creada a la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun operacionInsertarNota(nota: Nota): Long

    // busca y regresa una lista de notas que cumplan con el criterio de la consulta
    // (en este caso que o contenido o titulo coincidan con las palabras clave pasadas)
    @Query("SELECT * FROM Nota WHERE titulo LIKE '%' || :palabrasClave || '%' OR contenido LIKE '%' || :palabrasClave || '%'")
    suspend fun operacionGetListaDeNotas(palabrasClave: String): List<Nota>

    // busca y regresa la nota con el id especificado
    @Query("SELECT * FROM Nota WHERE id = :id")
    suspend fun operacionGetNotaPorId(id: String): Nota?

    @Update
    suspend fun operacionActualizarNota(nota: Nota)

    @Delete
    suspend fun operacionBorrarNota(nota: Nota)
}
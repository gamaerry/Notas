package gamaerry.notas.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gamaerry.notas.modelo.Nota

@Dao
interface DaoPrincipal {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun operacionInsertarNota(nota: Nota): Long

    @Query("SELECT * FROM Nota WHERE titulo LIKE '%' || :query || '%' OR contenido LIKE '%' || :query || '%'")
    suspend fun operacionGetListaDeNotas(query: String): List<Nota>
}
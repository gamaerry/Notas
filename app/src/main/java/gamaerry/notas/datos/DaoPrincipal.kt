package gamaerry.notas.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DaoPrincipal {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun operacionInsertarNota(nota: Nota): Long
}
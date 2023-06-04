package gamaerry.notas.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import gamaerry.notas.R
import java.util.UUID

@Entity
data class Nota (
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val contenido: String,
    val color: Int = R.color.blanco,
    val creacion: Long = System.currentTimeMillis(),
    val modificacion: Long = System.currentTimeMillis()
)
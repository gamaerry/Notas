package gamaerry.notas.datos
import gamaerry.notas.R
import java.util.UUID

data class Nota (
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val contenido: String,
    val color: Int = R.color.blanco,
    val creacion: Long = System.currentTimeMillis(),
    val modificacion: Long = System.currentTimeMillis()
)
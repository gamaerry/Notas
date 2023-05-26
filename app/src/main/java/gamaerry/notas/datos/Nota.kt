package gamaerry.notas.datos
import java.util.UUID

data class Nota (
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val contenido: String,
    val color: Int = android.R.color.background_light,
    val creacion: Long = System.currentTimeMillis(),
    val modificacion: Long = System.currentTimeMillis()
)
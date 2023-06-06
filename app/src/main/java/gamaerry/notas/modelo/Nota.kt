package gamaerry.notas.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import gamaerry.notas.R
import java.util.UUID

// este es el template o modelo que tomaran los objetos
// que eventualente ira creando el usuario en la aplicacion
// (notese que el id lo puede generar Room por nosotros,
// pero yo he decidido hacerlo con randomUUID() de java.util)
@Entity
data class Nota(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val contenido: String,
    val color: Int = R.color.blanco,
    val creacion: Long = System.currentTimeMillis(),
    val modificacion: Long = System.currentTimeMillis()
)
package gamaerry.notas.adaptadores

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gamaerry.notas.R
import gamaerry.notas.databinding.ItemNotaBinding
import gamaerry.notas.modelo.Nota
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListaDeNotasAdapter
@Inject
constructor() : ListAdapter<Nota, ListaDeNotasAdapter.ItemNotaViewHolder>(NotaDiffUtil) {
    // definira que pasara con el cada item a la hora de hacer click,
    // lo usara el viewHolder pero quien tiene que recibirlo es el adapter
    lateinit var accionAlHacerClic: ((String) -> Unit)

    // junto con onBindViewHolder, hacen posible la interacción adapter/viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNotaViewHolder =
        ItemNotaViewHolder(
            ItemNotaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemNotaViewHolder, i: Int) = holder.recoger(getItem(i))

    // el listAdapter es solo una especie de intermediario, es el viewHolder
    // quien se recoge cada elemento de la lista (gracias a onBindViewHolder)
    // para luego vaciarlo dentro de cada vista reusable (itemView)
    inner class ItemNotaViewHolder(binding: ItemNotaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val contenedor = binding.contenedorItemNota
        private val titulo = binding.tituloNota
        private val contenido = binding.contenidoNota
        private val fondoNota = contenedor.background as GradientDrawable

        fun recoger(nota: Nota) {
            // establece la accion al hacer clic en cada itemView
            // (itemView no esta definido en el xml, si no que es
            // un campo dentro de la clase viewHolder y representa
            // a cada item en su totalidad dentro del reciclerView)
            itemView.setOnClickListener { accionAlHacerClic(nota.id) }

            // cambia el color del background del parent de cada item
            fondoNota.color = AppCompatResources.getColorStateList(itemView.context, nota.color)

            // si el color especificado en el objeto nota es blanco,
            // entonces se dibuja un contorno negro, en caso contrario
            // se dibujara del color del mismo fondo
            if (nota.color == R.color.blanco)
                fondoNota.setStroke(2, ContextCompat.getColor(itemView.context, R.color.negro))
            else
                fondoNota.setStroke(2, ContextCompat.getColor(itemView.context, nota.color))

            // notese que al inicio cada textView esta
            // desaparecido, asi que solo apareceran de nuevo
            // si o el titulo o el contenido no estan vacios
            titulo.isVisible = nota.titulo.isNotEmpty()
            contenido.isVisible = nota.contenido.isNotEmpty()
            titulo.text = nota.titulo
            contenido.text = nota.contenido
        }
    }


    // un objeto necesario para el listAdapter al parecer su funcion es hacer
    // posible la distincion entre tener el mismo contenido o ser el mismo objeto
    object NotaDiffUtil : DiffUtil.ItemCallback<Nota>() {
        override fun areItemsTheSame(old: Nota, new: Nota): Boolean = old == new
        override fun areContentsTheSame(old: Nota, new: Nota): Boolean = old.id == new.id
    }
}

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
import gamaerry.notas.databinding.ItemNotaBinding
import gamaerry.notas.datos.Nota

class ListaDeNotasAdapter :
    ListAdapter<Nota, ListaDeNotasAdapter.ItemNotaViewHolder>(NotaDiffUtil) {
    private var accionAlHacerClic: ((String) -> Unit)? = null

    fun setAccionAlHacerClic(accion: (String) -> Unit) {
        accionAlHacerClic = accion
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNotaViewHolder {
        return ItemNotaViewHolder(
            ItemNotaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemNotaViewHolder, i: Int) {
        val nota = getItem(i)
        holder.enlazar(nota)
    }

    inner class ItemNotaViewHolder(binding: ItemNotaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val contenedor = binding.contenedorItemNota
        private val titulo = binding.tituloNota
        private val contenido = binding.contenidoNota
        private val fondoNotas = contenedor.background as GradientDrawable
        private var notaActual: Nota? = null

        init {
            itemView.setOnClickListener {
                notaActual?.let { notaActual ->
                    accionAlHacerClic?.let { it(notaActual.id) }
                }
            }
        }

        fun enlazar(nota: Nota) {
            fondoNotas.color = AppCompatResources.getColorStateList(itemView.context, nota.color)
            notaActual = nota

            if (nota.color == android.R.color.background_light)
                fondoNotas.setStroke(
                    2,
                    ContextCompat.getColor(itemView.context, android.R.color.background_dark)
                )
            else
                fondoNotas.setStroke(2, ContextCompat.getColor(itemView.context, nota.color))

            if (nota.titulo.isNotEmpty()) {
                titulo.isVisible = true
                titulo.text = nota.titulo
            }
            if (nota.contenido.isNotEmpty()) {
                contenido.isVisible = true
                contenido.text = nota.contenido
            }
        }
    }

    object NotaDiffUtil : DiffUtil.ItemCallback<Nota>() {
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem.id == newItem.id
        }

    }
}
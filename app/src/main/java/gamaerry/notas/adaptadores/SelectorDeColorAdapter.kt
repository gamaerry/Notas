package gamaerry.notas.adaptadores

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import gamaerry.notas.R
import gamaerry.notas.databinding.ItemColorBinding

// este adaptador a diferencia del ListaDeNotasAdapter implementa el
// Adapter simple, pues aqui se muestra una lista estatica que no cambiara
class SelectorDeColorAdapter(
    private var listaDeColores: List<Int>
) : RecyclerView.Adapter<SelectorDeColorAdapter.ColorSelectorViewHolder>() {
    // definira que pasara con el cada item a la hora de hacer click,
    // lo usara el viewHolder pero quien tiene que recibirlo es el adapter
    lateinit var accionAlHacerClic: (Int) -> Unit

    // junto con onBindViewHolder y getItemCount hacen posible la interacción adapter/viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ColorSelectorViewHolder(
        ItemColorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ColorSelectorViewHolder, i: Int) =
        holder.recoger(listaDeColores[i])

    override fun getItemCount() = listaDeColores.size

    inner class ColorSelectorViewHolder(
        binding: ItemColorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var circulo = binding.circulo
        private var icono = binding.icono
        private val colorDelCirculo = circulo.background as GradientDrawable

        // recoger() obtiene cada item de la lista de colores y
        // con las referencias a los elementos visuales "los pinta"
        fun recoger(color: Int) {
            // establece la accion al hacer clic en cada itemView
            itemView.setOnClickListener { accionAlHacerClic(color) }

            // pinta cada imageView circulo en el reciclerView
            colorDelCirculo.color =
                ColorStateList.valueOf(ContextCompat.getColor(itemView.context, color))

            // el icono se muestra si el color de la nota es el blanco
            icono.isVisible = color == R.color.blanco
        }
    }
}
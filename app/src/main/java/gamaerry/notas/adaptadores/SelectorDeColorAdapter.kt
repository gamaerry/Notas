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

class SelectorDeColorAdapter(
    private var listaDeColores: List<Int>
) : RecyclerView.Adapter<SelectorDeColorAdapter.ColorSelectorViewHolder>() {

    private var accionAlHacerClic: ((Int) -> Unit )? = null

    fun setAccionAlHacerClic(accion: (Int) -> Unit) {
        accionAlHacerClic = accion
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSelectorViewHolder {
        return ColorSelectorViewHolder(
            ItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ColorSelectorViewHolder, i: Int) {
        val color = listaDeColores[i]
        holder.bind(color)
    }

    override fun getItemCount(): Int {
        return listaDeColores.size
    }
    inner class ColorSelectorViewHolder(
        binding: ItemColorBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private var circulo = binding.circulo
        private var icono = binding.icono

        private val colorDelCirculo = circulo.background as GradientDrawable

        private var colorActual: Int? = null

        init {
            itemView.setOnClickListener {
                colorActual?.let { color ->
                    accionAlHacerClic?.let { it(color) }
                }
            }
        }

        fun bind(color: Int) {
            colorActual = color
            colorDelCirculo.color = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, color))
            icono.isVisible = color == R.color.blanco
        }

    }
}
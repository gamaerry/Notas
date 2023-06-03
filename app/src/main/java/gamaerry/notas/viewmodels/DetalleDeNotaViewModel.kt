package gamaerry.notas.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gamaerry.notas.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetalleDeNotaViewModel
@Inject
constructor(): ViewModel(){
    private val _colorSeleccionado = MutableStateFlow(R.color.blanco)
    val colorSeleccionado: StateFlow<Int> get() = _colorSeleccionado

    fun actualizarColor(color: Int){
        _colorSeleccionado.value = color
    }
}
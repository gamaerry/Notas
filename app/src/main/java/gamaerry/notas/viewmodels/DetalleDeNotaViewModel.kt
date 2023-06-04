package gamaerry.notas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gamaerry.notas.R
import gamaerry.notas.modelo.Nota
import gamaerry.notas.datos.RepositorioPrincipal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetalleDeNotaViewModel
@Inject
constructor(private val repositorio: RepositorioPrincipal) : ViewModel() {
    private val _colorSeleccionado = MutableStateFlow(R.color.blanco)
    val colorSeleccionado: StateFlow<Int> get() = _colorSeleccionado
    private val _nota = MutableStateFlow<Nota?>(null)
    val nota: StateFlow<Nota?> get() = _nota
    private val _laNotaSeHaModificado = MutableStateFlow(false)
    val laNotaSeHaModificado: StateFlow<Boolean> get() = _laNotaSeHaModificado


    fun actualizarColor(color: Int) {
        _colorSeleccionado.value = color
    }

    fun guardarCambios(titulo: String, contenido: String) {
        if (_nota.value == null) //es decir que apenas se esta creando no modificando
            guardarNuevaNota(titulo, contenido)
        else
            modificarNota(titulo, contenido)
    }

    private fun modificarNota(titulo: String, contenido: String) {
        // TODO:
    }

    private fun guardarNuevaNota(titulo: String, contenido: String) {
        if (titulo.isEmpty() && contenido.isEmpty()){
            _laNotaSeHaModificado.value = true // ??
            return
        }
        repositorio.insertarNota(
            Nota(
                titulo = titulo,
                contenido = contenido,
                color = _colorSeleccionado.value
            )
        ).onEach {
            _laNotaSeHaModificado.value = true
        }.launchIn(viewModelScope)
    }
}
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
    // aqui se almacena el color seleccionado para el fondo de la nota
    private val _colorSeleccionado = MutableStateFlow(R.color.blanco)
    val colorSeleccionado: StateFlow<Int> get() = _colorSeleccionado

    // aqui se almacena el objeto nota
    private val _nota = MutableStateFlow<Nota?>(null)
    val nota: StateFlow<Nota?> get() = _nota

    // establece el valor de la nota dada el id que se
    // especifica en la ListaDeNotasFragment al hacer click
    fun setNotaPorId(id: String) {
        repositorio.getNotaPorId(id).onEach {
            if (it == null) setColor(R.color.blanco)
            _nota.value = it
        }.launchIn(viewModelScope)
    }

    // se actualiza el color del fondo del detalle de la nota
    fun setColor(color: Int) {
        _colorSeleccionado.value = color
    }

    // se guardan cambios o nueva nota
    // dependiendo si ya se había creado o no
    fun guardarNota(titulo: String, contenido: String) {
        // si el valor de la nota es null significa que no se ha creado
        if (_nota.value == null)
            guardarNuevaNota(titulo, contenido)
        else
            guardarCambios(titulo, contenido)
    }

    private fun guardarCambios(titulo: String, contenido: String) {
        repositorio.actualizarNota(
            _nota.value!!.copy(
                titulo = titulo,
                contenido = contenido,
                color = _colorSeleccionado.value,
                modificacion = System.currentTimeMillis()
            )
        ).launchIn(viewModelScope)
    }

    private fun guardarNuevaNota(titulo: String, contenido: String) {
        //si titulo y contenido estan ambos vacios, entonces no se guarda nada
        if (titulo.isEmpty() && contenido.isEmpty()) return
        // pasada esa validacion se procede a guardar la nota en
        // la base de datos de acuerdo a la especificacion en el dao
        repositorio.insertarNota(
            Nota(
                titulo = titulo,
                contenido = contenido,
                color = _colorSeleccionado.value
            )
        ).launchIn(viewModelScope)
    }

    fun insertarNota(nota: Nota) {
        repositorio.insertarNota(nota).launchIn(viewModelScope)
    }

    fun borrarNota() {
        _nota.value?.let { repositorio.borrarNota(it).launchIn(viewModelScope) }
    }
}

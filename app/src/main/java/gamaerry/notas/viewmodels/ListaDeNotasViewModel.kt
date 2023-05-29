package gamaerry.notas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gamaerry.notas.datos.Nota
import gamaerry.notas.datos.RepositorioDeNotas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListaDeNotasViewModel
@Inject
constructor(
    private val repositorio: RepositorioDeNotas
) : ViewModel() {
    private var _listaDeNotas = MutableStateFlow<List<Nota>>(emptyList())
    val listaDeNotas: StateFlow<List<Nota>> get() = _listaDeNotas

    init {
        getNotas()
    }

    fun getNotas() {
        repositorio.getFlujoDeListaDeNotas().onEach {
            _listaDeNotas.value = it
        }.launchIn(viewModelScope)
    }
}
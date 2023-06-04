package gamaerry.notas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gamaerry.notas.modelo.Nota
import gamaerry.notas.datos.RepositorioPrincipal
import gamaerry.notas.getNotasEstaticas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListaDeNotasViewModel
@Inject
constructor(
    private val repositorio: RepositorioPrincipal
) : ViewModel() {
//    private var _listaDeNotas = MutableStateFlow<List<Nota>>(emptyList())
    private var _listaDeNotas = MutableStateFlow(getNotasEstaticas()) //es posible pasar directamente la lista de notas
    val listaDeNotas: StateFlow<List<Nota>> get() = _listaDeNotas

//    init {
//        getNotas()
//    }

    private fun getNotas() {
        repositorio.getFlujoDeListaDeNotas().onEach {
            _listaDeNotas.value = it
        }.launchIn(viewModelScope)
    }
}
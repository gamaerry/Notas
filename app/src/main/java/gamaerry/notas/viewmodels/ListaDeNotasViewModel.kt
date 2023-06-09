package gamaerry.notas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gamaerry.notas.modelo.Nota
import gamaerry.notas.datos.RepositorioPrincipal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListaDeNotasViewModel
@Inject
constructor(private val repositorio: RepositorioPrincipal) : ViewModel() {
    // aqui es donde se almacena la listaDeNotas
    private var _listaDeNotas = MutableStateFlow<List<Nota>>(emptyList())
    val listaDeNotas: StateFlow<List<Nota>> get() = _listaDeNotas
    private var _esLineal = MutableStateFlow<Boolean>(true)
    val esLineal: StateFlow<Boolean> get() = _esLineal

    // representa el filtrado de busqueda
    private val busquedaQuery = MutableStateFlow("")

    // obtiene las notas del repositorio de acuerdo a la busqueda
    fun getNotas() = repositorio.getListaDeNotas(busquedaQuery.value).onEach {
        _listaDeNotas.value = it
    }.launchIn(viewModelScope)

    fun setBusquedaQuery(query: String): Boolean{
        busquedaQuery.value = query
        getNotas()
        return true
    }

    fun setEsLineal(esLineal: Boolean){
        _esLineal.value = esLineal
    }
}
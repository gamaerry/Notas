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

// el ViewModelPrincipal es quiza la clase más importante del codigo, pues
// establece el enlace del repositorio (y sus operaciones) con el uso que
// se le de a los datos resultantes por parte de las vistas de los fragmentos
@HiltViewModel
class ViewModelPrincipal
@Inject
constructor(private val repositorio: RepositorioPrincipal) : ViewModel() {
    // aqui es donde se almacena la listaDeNotas
    private var _listaDeNotas = MutableStateFlow<List<Nota>>(emptyList())
    val listaDeNotas: StateFlow<List<Nota>> get() = _listaDeNotas

    // guarda se el acomodo de la lista es lineal o no
    private var _esLineal = MutableStateFlow(true)
    val esLineal: StateFlow<Boolean> get() = _esLineal

    // aqui se almacena el color seleccionado para el fondo de la nota
    private val _colorSeleccionado = MutableStateFlow(R.color.blanco)
    val colorSeleccionado: StateFlow<Int> get() = _colorSeleccionado

    // aqui se almacena el objeto Nota a mostrar
    private val _notaActual = MutableStateFlow<Nota?>(null)
    val notaActual: StateFlow<Nota?> get() = _notaActual

    // representa el filtrado de busqueda
    private val palabrasClave = MutableStateFlow("")

    // obtiene las notas del repositorio de acuerdo a la busqueda
    fun getNotas() = repositorio.getListaDeNotas(palabrasClave.value).onEach {
        _listaDeNotas.value = it
    }.launchIn(viewModelScope)

    // establece las palabras clave que devolveran la lista en cada busqueda
    // (notese que si palabrasClave.value es "" room devolvera toda la base de datos)
    fun setPalabrasClave(busqueda: String): Boolean {
        palabrasClave.value = busqueda
        // es necesario actualizar en cada actualizacion
        // de palabras clave nuestra listaDeNotas.value
        getNotas()
        // devuelve true para indicar una busqueda exitosa
        return true
    }

    // establece el valor de esLineal tanto al inicio de cada
    // lanzamiento como a la hora de presionar el icono correspondiente
    fun setEsLineal(esLineal: Boolean) {
        _esLineal.value = esLineal
    }

    // establece el valor de la nota dada el id que se
    // especifica en la ListaDeNotasFragment al hacer click
    fun setNotaActualPorId(id: String) {
        repositorio.getNotaPorId(id).onEach {
            if (it == null) setColor(R.color.blanco)
            _notaActual.value = it
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
        if (_notaActual.value == null)
            guardarNuevaNota(titulo, contenido)
        else
            guardarCambios(titulo, contenido)
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

    // esta implementacion especifica para guardar una nota solo es usada la
    // primera vez que se ejecuta la aplicacion para insertar la nota de bienvenida
    // (notese que en guardarNuevaNota() de este viewModel es una implementacion diferente
    // donde se inserta una Nota tomando unicamente su contenido, titulo y color)
    fun insertarNota(nota: Nota) {
        repositorio.insertarNota(nota).launchIn(viewModelScope)
    }

    // borrarNota() se ejecuta mediante el icono de eliminar, el cual solo es
    // visible cuando _notaActual.value es distinto de null, por eso el operador !!
    fun borrarNota() {
        repositorio.borrarNota(_notaActual.value!!).launchIn(viewModelScope)
    }

    // al igual que borrarNota(), esta solo se ejecuta cuando _notaActual.value es
    // distinto de null, note además que copy() regresa el mismo objeto modificado
    private fun guardarCambios(titulo: String, contenido: String) {
        repositorio.actualizarNota(
            _notaActual.value!!.copy(
                titulo = titulo,
                contenido = contenido,
                color = _colorSeleccionado.value,
                modificacion = System.currentTimeMillis()
            )
        ).launchIn(viewModelScope)
    }
}

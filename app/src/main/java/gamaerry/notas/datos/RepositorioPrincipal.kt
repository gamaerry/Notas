package gamaerry.notas.datos

import gamaerry.notas.getNotaEstatica
import gamaerry.notas.modelo.Nota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

// el dao es un objeto mediante el cual realizamos operaciones elementales
// y el resultado de esas operaciones las va a recibir este repositorio
// que actuara como intermediario entre dichas operaciones y los viewModel
// necesariamente en forma de flows
@Singleton
class RepositorioPrincipal
@Inject
constructor(private val daoPrincipal: DaoPrincipal) {
    // a la hora de escribir estas anotaciones, todavia
    // no conozco a detalle para que ocupa el long quien
    // vaya a llamar a esta funcion, originalmente se emitia
    // un booleano, sin embargo presiento que asi es mas practico
    fun insertarNota(nota: Nota) = flow {
        emit(daoPrincipal.operacionInsertarNota(nota))
    }.catch { it.printStackTrace() }

    // el view model que llama a esta funcion lo hace
    // mediante una especie de bloque suspendible (.onEach)
    fun getListaDeNotas(palabrasClave: String) = flow {
        emit(daoPrincipal.operacionGetListaDeNotas(palabrasClave))
    }.catch { it.printStackTrace() }

    // emite el objeto Nota obtenido por el Dao dada su id
    fun getNotaPorId(id: String) = flow {
        if (id == "")
            emit(null)
        else
            emit(daoPrincipal.operacionGetNotaPorId(id))
    }.catch { it.printStackTrace() }
    
    fun actualizarNota(nota: Nota) = flow {
        emit(daoPrincipal.operacionActualizarNota(nota))
    }.catch { it.printStackTrace() }

    fun borrarNota(nota: Nota) = flow {
        emit(daoPrincipal.operacionBorrarNota(nota))
    }.catch { it.printStackTrace() }
}
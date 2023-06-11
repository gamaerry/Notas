package gamaerry.notas.datos

import gamaerry.notas.modelo.Nota
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
    // notese que en todas las operaciones del repositorio se
    // emite lo que especifica la funcion suspendible del dao
    // (en el caso de estas tres primeras funciones no se emiten nada
    // -Unit- al tratarse de funciones que no devuelven nada en el dao)
    fun insertarNota(nota: Nota) = flow<Unit> {
        daoPrincipal.operacionInsertarNota(nota)
    }.catch { it.printStackTrace() }

    fun borrarNota(nota: Nota) = flow<Unit> {
        daoPrincipal.operacionBorrarNota(nota)
    }.catch { it.printStackTrace() }

    fun actualizarNota(nota: Nota) = flow<Unit> {
        daoPrincipal.operacionActualizarNota(nota)
    }.catch { it.printStackTrace() }

    // el view model que llama a esta funcion lo
    // hace mediante una especie de bloque suspendible
    // (.onEach) para recoger la cada sublista que se
    // emite (en el caso de una busqueda)
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
}
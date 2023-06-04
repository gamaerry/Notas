package gamaerry.notas.datos

import gamaerry.notas.getNotasEstaticas
import gamaerry.notas.modelo.Nota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioPrincipal
@Inject
constructor(private val daoPrincipal: DaoPrincipal) {
    fun insertarNota(nota: Nota): Flow<Boolean> = flow {
        daoPrincipal.operacionInsertarNota(nota)
        emit(true)
    }.catch { it.printStackTrace() }

    fun getFlujoDeListaDeNotas(): Flow<List<Nota>> = flow {
        emit(getNotasEstaticas())
    }.catch { it.printStackTrace() }
}
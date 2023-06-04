package gamaerry.notas.datos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioPrincipal
@Inject
constructor(){
    fun getFlujoDeListaDeNotas(): Flow<List<Nota>> = flow {
        emit(getListaDeNotas())
    }.catch { it.printStackTrace() }
}
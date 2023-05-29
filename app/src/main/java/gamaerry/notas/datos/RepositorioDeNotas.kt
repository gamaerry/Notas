package gamaerry.notas.datos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepositorioDeNotas {
    fun getFlujoDeListaDeNotas(): Flow<List<Nota>> = flow {
        emit(getListaDeNotas())
    }.catch { it.printStackTrace() }
}
package gamaerry.notas.datos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepositorioDeNotas {
    fun getFlujoDeNotas(): Flow<List<Nota>> = flow {
        emit(getNotas())
    }.catch { it.printStackTrace() }
}
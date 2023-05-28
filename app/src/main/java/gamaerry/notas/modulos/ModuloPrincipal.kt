package gamaerry.notas.modulos

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gamaerry.notas.adaptadores.ListaDeNotasAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloPrincipal {
    @Provides
    @Singleton
    fun proveerListaDeNotasAdapter() = ListaDeNotasAdapter()
}
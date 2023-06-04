package gamaerry.notas.modulos

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gamaerry.notas.R
import gamaerry.notas.adaptadores.SelectorDeColorAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloPrincipal {
    @Provides
    @Singleton
    fun proveerSelectorDeColorAdapter() = SelectorDeColorAdapter(
        listOf(
            R.color.blanco,
            R.color.rojo,
            R.color.rosa,
            R.color.naranja1,
            R.color.naranja2,
            R.color.amarillo,
            R.color.verde1,
            R.color.verde2,
            R.color.verde3,
            R.color.azul1,
            R.color.azul2,
            R.color.morado,
        )
    )
}
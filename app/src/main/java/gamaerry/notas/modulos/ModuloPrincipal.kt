package gamaerry.notas.modulos

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gamaerry.notas.R
import gamaerry.notas.adaptadores.SelectorDeColorAdapter
import gamaerry.notas.datos.BaseDeDatosPrincipal
import gamaerry.notas.datos.BaseDeDatosPrincipal.Companion.NOMBRE_BASE_DE_DATOS
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

    @Provides
    @Singleton
    fun proveerBaseDeDatosPrincipal(app: Application): BaseDeDatosPrincipal = Room
        .databaseBuilder(app, BaseDeDatosPrincipal::class.java, NOMBRE_BASE_DE_DATOS)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun proveerDaoPrincipal(baseDeDatos: BaseDeDatosPrincipal) = baseDeDatos.getNotaDao()
}
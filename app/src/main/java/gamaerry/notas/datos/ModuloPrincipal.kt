package gamaerry.notas.datos

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gamaerry.notas.adaptadores.SelectorDeColorAdapter
import gamaerry.notas.datos.BaseDeDatosPrincipal.Companion.NOMBRE_BASE_DE_DATOS
import gamaerry.notas.getColores
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloPrincipal {
    @Provides
    @Singleton
    fun proveerSelectorDeColorAdapter() = SelectorDeColorAdapter(getColores())

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
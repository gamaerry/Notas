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

// el objeto modulo nos sirve para proveer las
// implementaciones especificas de cada tipo
// (clase o interfaz) que se requieran inyectar
@Module
@InstallIn(SingletonComponent::class)
object ModuloPrincipal {
    // Para la instancia de SelectorDeColorAdapter se necesita la lista de colores a usar
    @Provides
    @Singleton
    fun proveerSelectorDeColorAdapter() = SelectorDeColorAdapter(getColores())

    // La instancia de base de datos se crea exclusivamente
    // con una funcion propia de la librería de Room
    @Provides
    @Singleton
    fun proveerBaseDeDatosPrincipal(app: Application): BaseDeDatosPrincipal = Room
        .databaseBuilder(app, BaseDeDatosPrincipal::class.java, NOMBRE_BASE_DE_DATOS)
        .fallbackToDestructiveMigration()
        .build()

    // La instancia del dao se crea a partir de la base de datos ya proveida
    @Provides
    @Singleton
    fun proveerDaoPrincipal(baseDeDatos: BaseDeDatosPrincipal) = baseDeDatos.getNotaDao()
}
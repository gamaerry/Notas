package gamaerry.notas

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// launcher necesario para la inyeccion de dependencias de Hilt
// (notese que es de aqui donde el modulo de hilt obtiene la instancia
// de Application que requiere la creacion de la base de datos)
@HiltAndroidApp class App : Application()
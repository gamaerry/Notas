package gamaerry.notas

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Launcher necesario para la inyeccion de dependencias de Hilt
@HiltAndroidApp class App : Application()
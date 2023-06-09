package gamaerry.notas

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import gamaerry.notas.modelo.Nota

val Activity.myPrefs: SharedPreferences
    get() = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

fun Activity.getEsPrimeraVez(): Boolean {
    val esPrimeraVez = myPrefs.getBoolean("esPrimeraVez", true)
    myPrefs.edit().putBoolean("esPrimeraVez", false).apply()
    return esPrimeraVez
    // nota tecnica: a pesar de que a esPrimeraVez se le
    // asigna un objeto Boolean esta no se actualiza cuando dicho
    // objeto se actualiza, es decir a pesar de que Boolean
    // tecnicamente no es un tipo primitivo en kotlin asi es
    // manejado internamente, justo como los primitivos en java
}

fun Activity.getEsLineal(): Boolean {
    val esLineal = esLineal()
    myPrefs.edit().putBoolean("esLineal", !esLineal).apply()
    return !esLineal
}

fun Activity.esLineal() = myPrefs.getBoolean("esLineal", true)

// ocultarTeclado() se usa a la hora de presionar "buscar" en el fragmento principal
fun View.ocultarTeclado() {
    val manejadorDelInputMethod =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.hideSoftInputFromWindow(windowToken, 0)
    this.clearFocus()
}

// mostrarTeclado() se usa cuando el usuario abre una nota (ya sea para modificar o para crear)
fun View.mostrarTeclado() {
    this.requestFocus()
    val manejadorDelInputMethod =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

// esta junto con cambiarColorDelBackground() extienden a sus clases correspondientes
// para hacer un cambio de color cuando asi se requiera por el usuario
fun Window.cambiarColorDelStatusBar(color: Int) {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    statusBarColor = ContextCompat.getColor(context, color)
}

fun ConstraintLayout.cambiarColorDelBackground(color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}

// nota estatica que se agrega a la lista de las notas de la base
// de datos (probablemente borrada para la version de lanzamiento)
fun getNotaEstatica() = Nota(
    titulo = "Nota de bienvenida",
    contenido = "Bienvenido(a) a esta aplicación de notas totalmente add-free, ¡que la disfrutes! <3\n\nAtentamente, el desarrollador."
)

// toma y regresa los colores definidos en colors.xml
// que terminara usando el SelectorDeColorFragment()
fun getColores() = listOf(
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
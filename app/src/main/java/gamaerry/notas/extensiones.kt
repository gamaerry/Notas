package gamaerry.notas

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import gamaerry.notas.modelo.Nota

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
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.statusBarColor = ContextCompat.getColor(context, color)
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
package gamaerry.notas

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import gamaerry.notas.modelo.Nota

fun View.mostrarTeclado() {
    this.requestFocus()
    val manejadorDelInputMethod =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Window.cambiarColorDelStatusBar(color: Int) {
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.statusBarColor = ContextCompat.getColor(context, color)
}

fun ConstraintLayout.cambiarColorDelBackground(color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}

fun getNotaEstatica() = Nota(
    titulo = "Nota de bienvenida",
    contenido = "Bienvenido(a) a esta aplicación de notas totalmente add-free, ¡que la disfrutes! <3\n\nAtentamente, el desarrollador."
)

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
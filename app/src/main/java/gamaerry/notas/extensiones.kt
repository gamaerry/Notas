package gamaerry.notas

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import gamaerry.notas.modelo.Nota

fun View.ocultarTeclado() {
    val manejadorDelInputMethod =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.hideSoftInputFromWindow(windowToken, 0)
    this.clearFocus()
}

fun View.mostrarTeclado() {
    this.requestFocus()
    val manejadorDelInputMethod =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun getNotasEstaticas() = listOf(
    Nota(
        titulo = "Nota de bienvenida",
        contenido = "Bienvenido(a) a esta aplicación de notas totalmente add-free, ¡que la disfrutes! <3\n\nAtentamente, el desarrollador."
    )
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
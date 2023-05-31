package gamaerry.notas.utilidades

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.ocultarTeclado(){
    val manejadorDelInputMethod = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.hideSoftInputFromWindow(windowToken, 0)
    this.clearFocus()
}

fun View.mostrarTeclado(){
    this.requestFocus()
    val manejadorDelInputMethod = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manejadorDelInputMethod.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
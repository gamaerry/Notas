package gamaerry.notas

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import gamaerry.notas.modelo.Nota
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// se crea una "variable de extension" para tener
// siempre una referencia al SharedPreferences principal
val Activity.myPrefs: SharedPreferences
    get() = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

// devuelve si es la primera vez que inicia la app.
// Notese que una vez obtenida true siempre sera false
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

// ademas de cambiar al valor contrario lo devuelve
fun Activity.toggleEsLineal(): Boolean {
    val esLineal = getEsLineal()
    myPrefs.edit().putBoolean("esLineal", !esLineal).apply()
    return !esLineal
}

// solo devuelte el valor booleano (a diferencia de toggleEsLineal())
fun Activity.getEsLineal() = myPrefs.getBoolean("esLineal", true)

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

// funcion de extension que dado el currentMillis de modificacion
// obtiene su correspondiente string con el formato adecuado
fun Long.getFechaDeModificacion(): String {
    val fechaDeModificacion = Date(this)
    val formatoEstablecido: SimpleDateFormat =
        if (DateUtils.isToday(fechaDeModificacion.time))
            SimpleDateFormat("hh:mm a", Locale.ROOT)
        else
            SimpleDateFormat("MMM dd", Locale.ROOT)
    return "Editado ${formatoEstablecido.format(fechaDeModificacion)}"
}

// nota estatica que se agrega a la lista de las notas de la base de datos
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
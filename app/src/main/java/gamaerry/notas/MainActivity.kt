package gamaerry.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.fragmentos.ListaDeNotasFragment

// esta activity no necesita binding, pues el unico elemento que contiene
// (el FragmentContainerView) lo administra el campo supportFragmentManager
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // con esta condicion me aseguro de que esta linea
        // se ejecute unicamente cuando se lanza la app
        // y no cada vez que se cree la activity
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.contenedorPrincipal, ListaDeNotasFragment()).commit()
    }
}
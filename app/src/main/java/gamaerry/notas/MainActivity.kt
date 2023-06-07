package gamaerry.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.fragmentos.ListaDeNotasFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // con esta condicion me aseguro de que esta linea
        // se ejecute unicamente cuando se lanza la app
        // y no cada vez que se cree la activity
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.contenedorPrincipal, ListaDeNotasFragment()).commit()
    }
}
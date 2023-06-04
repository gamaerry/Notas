package gamaerry.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.databinding.ActivityMainBinding
import gamaerry.notas.fragmentos.ListaDeNotasFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.contenedorPrincipal, ListaDeNotasFragment()).commit()
    }
}
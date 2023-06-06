package gamaerry.notas.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.R
import gamaerry.notas.adaptadores.ListaDeNotasAdapter
import gamaerry.notas.cambiarColorDelStatusBar
import gamaerry.notas.databinding.FragmentListaDeNotasBinding
import gamaerry.notas.viewmodels.ListaDeNotasViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListaDeNotasFragment : Fragment() {
    // los binding enlazan las vistas con el codigo
    private var _binding: FragmentListaDeNotasBinding? = null
    private val binding get() = _binding!!

    // con by viewModels() uso la implementacion que me permite usar
    // este objeto exclusivamente dentro del alcance de este fragment
    private val listaDeNotasViewModel: ListaDeNotasViewModel by viewModels()

    // gracias a la inyeccion de dependencias no tengo que usar el constructor
    @Inject
    lateinit var listaDeNotasAdapter: ListaDeNotasAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // actualizar color y notas (obtenidas estas por el viewModel)
        requireActivity().window.cambiarColorDelStatusBar(R.color.principal)
        listaDeNotasViewModel.getNotas()
        // establecer accion para el boton de nueva nota
        binding.nuevaNota.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                // establece las animaciones tanto para el
                // fragmento que va saliendo de escena,
                // como para el que va entrando
                setCustomAnimations(
                    R.anim.entrar_desde_derecha,
                    R.anim.salir_hacia_izquierda,
                    R.anim.entrar_desde_izquierda,
                    R.anim.salir_hacia_derecha
                )
                // haciendo uso de replace() hago que el fragment que va saliendo
                // se destruya completamente, eliminando por completo la
                // responsabilidad del gestor de recursos de android de
                // mantener vivo al fragmento (como sucede con add())
                replace(R.id.contenedorPrincipal, DetalleDeNotaFragment())
                // no obstante la sentencia anterior, con addToBackStack()
                // me aseguro que a la hora de realizar el gesto de back
                // en el dispositivo, me regrese (cree de nuevo) a mi
                // fragmento anterior (ListaDeNotasFragment en este caso)
                addToBackStack("detalleNuevaNota")
                // inicio la transición
                commit()
            } // TODO: probablemente sería mas adecuado si creo el objeto FragmentTransaction y como ejecuto commit() a la hora de pulsar nuevaNota
        }
        // pasar las notas del viewModel al listAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                listaDeNotasViewModel.listaDeNotas.collect { listaDeNotasAdapter.submitList(it) }
            }
        }
        // enlazar el listAdapter al recyclerView
        binding.listaRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listaDeNotasAdapter
        }
    }

    // se sobreescriben estos metodos unicamente para
    // el correcto funcionamiento del nuestro binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaDeNotasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
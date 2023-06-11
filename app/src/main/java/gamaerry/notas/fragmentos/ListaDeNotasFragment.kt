package gamaerry.notas.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.R
import gamaerry.notas.adaptadores.ListaDeNotasAdapter
import gamaerry.notas.cambiarColorDelStatusBar
import gamaerry.notas.databinding.FragmentListaDeNotasBinding
import gamaerry.notas.getEsLineal
import gamaerry.notas.toggleEsLineal
import gamaerry.notas.getEsPrimeraVez
import gamaerry.notas.getNotaEstatica
import gamaerry.notas.ocultarTeclado
import gamaerry.notas.viewmodels.ViewModelPrincipal
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListaDeNotasFragment : Fragment() {
    // los binding enlazan las vistas con el codigo
    private var _binding: FragmentListaDeNotasBinding? = null
    private val binding get() = _binding!!

    // con by viewModels() uso la implementacion que me permite usar
    // este objeto exclusivamente dentro del alcance de este fragment
    private val viewModelPrincipal: ViewModelPrincipal by activityViewModels()

    // gracias a la inyeccion de dependencias no tengo que usar el constructor
    @Inject
    lateinit var listaDeNotasAdapter: ListaDeNotasAdapter

    // no puede ser un lambda porque implementa dos metodos en lugar de uno
    private val accionAlBuscar = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
        // esta funcion se llama cuando se presiona el
        // icono de buscar en el SearchView o en el teclado
        override fun onQueryTextSubmit(query: String?): Boolean {
            return if (query != null) {
                binding.buscador.ocultarTeclado()
                viewModelPrincipal.setPalabrasClave(query)
            } else false
        }

        // y esta se llama cuando cambia el texto introducido
        // (notese que en ambas funciones setBusquedaQuery()
        // regresa un true indicando su correcto funcionamiento)
        override fun onQueryTextChange(query: String?): Boolean {
            return if (query != null)
                viewModelPrincipal.setPalabrasClave(query)
            else false
        }
    }

    // aqui es donde se cambia el icono y la manera de organizar el recyclerView
    private val accionAlCambiarEsLineal: (Boolean) -> Unit = {
        if (it) {
            binding.listaRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_cuadricula)
            )
        } else {
            binding.listaRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_lista)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // si es la primera vez que se inicia, la app da la bienvenida al usuario
        if (requireActivity().getEsPrimeraVez())
            viewModelPrincipal.insertarNota(getNotaEstatica())

        // actualizar color (del xml), las notas (en la base de datos)
        // y se estan ordenadas lineamente (en myPrefs del activity)
        requireActivity().window.cambiarColorDelStatusBar(R.color.principal)
        viewModelPrincipal.getNotas()
        viewModelPrincipal.setEsLineal(requireActivity().getEsLineal())

        // se establece el listener que
        binding.buscador.setOnQueryTextListener(accionAlBuscar)

        // establecer accion para el boton de nueva nota
        binding.nuevaNota.setOnClickListener {
            // esto dara un objeto Nota nulo
            // (notese que es set y no get por
            // tratarse del viewModelPrincipal)
            viewModelPrincipal.setNotaActualPorId("")
            getTransicion(requireActivity()).commit()
        }

        // hace la misma transicion pero con la nota seleccionada
        listaDeNotasAdapter.accionAlHacerClic = {
            // esto dara el objeto Nota con el id
            // obtenido del ItemNotaViewHolder
            viewModelPrincipal.setNotaActualPorId(it)
            getTransicion(requireActivity()).commit()
        }

        // obtiene y cambia el valor booleano esLineal
        binding.imageView.setOnClickListener {
            viewModelPrincipal.setEsLineal(requireActivity().toggleEsLineal())
        }

        // enlazar el listAdapter al recyclerView
        binding.listaRecyclerView.adapter = listaDeNotasAdapter

        // establece la accion al cambiar esLineal
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModelPrincipal.esLineal.collect { accionAlCambiarEsLineal(it) }
            }
        }

        // pasar las notas del viewModel al listAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModelPrincipal.listaDeNotas.collect { listaDeNotasAdapter.submitList(it) }
            }
        }
    }


    // esta transicion se hace independientemente si
    // se trata de una nueva nota o una ya existente
    private fun getTransicion(activity: FragmentActivity): FragmentTransaction{
        return activity.supportFragmentManager.beginTransaction().apply {
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
            addToBackStack("detalleNota")
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
package gamaerry.notas.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.cambiarColorDelBackground
import gamaerry.notas.cambiarColorDelStatusBar
import gamaerry.notas.databinding.FragmentDetalleDeNotaBinding
import gamaerry.notas.getFechaDeModificacion
import gamaerry.notas.modelo.Nota
import gamaerry.notas.mostrarTeclado
import gamaerry.notas.viewmodels.ViewModelPrincipal
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleDeNotaFragment : Fragment() {
    // los binding enlazan las vistas con el codigo
    private var _binding: FragmentDetalleDeNotaBinding? = null
    private val binding get() = _binding!!

    // con by activityViewModels() uso la implementacion que me
    // permite usar este objeto dentro del alcance de la actividad
    private val viewModelPrincipal: ViewModelPrincipal by activityViewModels()

    // notese que con el elvis operator nos aseguramos de que si la nota es nula nada
    // de esto se ejecutaria, y asi se evitarian errores relacionados con los nulos
    // (es decir, esto se ejecuta unicamente suando se abre una nota existente)
    private val accionAlCambiarNotaActual: (Nota?) -> Unit = {
        it?.let { nota ->
            binding.tituloNota.setText(nota.titulo)
            binding.contenidoNota.setText(nota.contenido)
            viewModelPrincipal.setColor(nota.color)
            binding.eliminar.isVisible = true
            binding.fechaDeEditado.text = nota.modificacion.getFechaDeModificacion()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // cada vez que entra este fragment se muestra el teclado
        binding.contenidoNota.mostrarTeclado()

        // aqui no se necesita ningún FragmentTransaction para mostrar el
        // SelectorDeColorFragment, solo se requiere esta sola linea de codigo
        binding.colorDeNota.setOnClickListener {
            SelectorDeColorFragment().show(requireActivity().supportFragmentManager, "selector")
        }

        binding.eliminar.setOnClickListener {
            //borra la nota de la base de datos con ayuda del repositorio
            viewModelPrincipal.borrarNota()
            //regresa al fragmento anterior
            requireActivity().supportFragmentManager.popBackStack()
        }

        // cada vez que entra este fragment es necesario actualizar los colores
        // del StatusBar y del Background (color almacenado en el viewModel)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModelPrincipal.colorSeleccionado.collect {
                    binding.parentDetalleDeNota.cambiarColorDelBackground(it)
                    requireActivity().window.cambiarColorDelStatusBar(it)
                }
            }
        }

        // establece la accion a realizar al cambiar el valor de la notaActual del viewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModelPrincipal.notaActual.collect { accionAlCambiarNotaActual }
            }
        }

        // unicamente en este fragment la accion de back (retroceder)
        // se sobreescribe para guardar la nota (funcion presente en el viewModel
        // y que depende de su objeto repositorio para hacerlo) y para
        // regresar al fragmento anterior (ListaDeNotasFragment en este caso)
        binding.atras.setOnClickListener { retroceder() }
        requireActivity().onBackPressedDispatcher.addCallback { retroceder() }
    }

    private fun retroceder() {
        // esta funcion no requiere pasar el color porque internamente
        // se toma del StateFlow colorSeleccionado del viewModel
        viewModelPrincipal.guardarNota(
            binding.tituloNota.text.toString(),
            binding.contenidoNota.text.toString()
        )

        //regresa al fragmento anterior
        requireActivity().supportFragmentManager.popBackStack()
    }

    // se sobreescriben estos metodos unicamente para
    // el correcto funcionamiento del nuestro binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleDeNotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
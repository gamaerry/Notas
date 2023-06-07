package gamaerry.notas.fragmentos

import android.os.Bundle
import android.text.format.DateUtils
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
import gamaerry.notas.mostrarTeclado
import gamaerry.notas.viewmodels.DetalleDeNotaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class DetalleDeNotaFragment : Fragment() {
    // los binding enlazan las vistas con el codigo
    private var _binding: FragmentDetalleDeNotaBinding? = null
    private val binding get() = _binding!!

    // con by activityViewModels() uso la implementacion que me
    // permite usar este objeto dentro del alcance de la actividad
    private val detalleDeNotaViewModel: DetalleDeNotaViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // cada vez que entra este fragment se muestra el teclado
        binding.contenidoNota.mostrarTeclado()

        // aqui no se necesita ningún FragmentTransaction para mostrar el
        // SelectorDeColorFragment, solo se requiere esta sola linea de codigo
        binding.colorDeNota.setOnClickListener {
            SelectorDeColorFragment().show(requireActivity().supportFragmentManager, "selector")
        }

        // cada vez que entra este fragment es necesario actualizar los colores
        // del StatusBar y del Background (color almacenado en el viewModel)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                detalleDeNotaViewModel.colorSeleccionado.collect {
                    binding.parentDetalleDeNota.cambiarColorDelBackground(it)
                    requireActivity().window.cambiarColorDelStatusBar(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                detalleDeNotaViewModel.nota.collect {
                    it?.let { nota ->
                        binding.tituloNota.setText(nota.titulo)
                        binding.contenidoNota.append("${nota.contenido} ")
                        detalleDeNotaViewModel.setColor(nota.color)
                        binding.eliminar.isVisible = true

                        val dateFormat: SimpleDateFormat =
                            if (DateUtils.isToday(Date(nota.modificacion).time))
                                SimpleDateFormat("hh:mm a", Locale.ROOT)
                            else
                                SimpleDateFormat("MMM dd", Locale.ROOT)

                        val fechaDeEditado = "Editado ${dateFormat.format(Date(nota.modificacion))}"
                        binding.fechaDeEditado.text = fechaDeEditado

                    }
                }
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
        // esta funcion no requiere del color porque el SelectorDeColorFragment
        // es quien se encarga de almacenar el color seleccionado por el usuario
        detalleDeNotaViewModel.guardarNota(
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
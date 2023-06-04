package gamaerry.notas.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.cambiarColorDelStatusBar
import gamaerry.notas.databinding.FragmentDetalleDeNotaBinding
import gamaerry.notas.mostrarTeclado
import gamaerry.notas.viewmodels.DetalleDeNotaViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleDeNotaFragment : Fragment() {
    private var _binding: FragmentDetalleDeNotaBinding? = null
    private val binding get() = _binding!!
    private val detalleDeNotaViewModel: DetalleDeNotaViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contenidoNota.mostrarTeclado()
        binding.colorDeNota.setOnClickListener {
            SelectorDeColorFragment().show(requireActivity().supportFragmentManager, "selector")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                detalleDeNotaViewModel.colorSeleccionado.collect {
                    binding.parentDetalleDeNota.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), it)
                    )
                    requireActivity().window.cambiarColorDelStatusBar(it)
                }
            }
        }
        binding.atras.setOnClickListener { guardar() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = guardar()
            }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                detalleDeNotaViewModel.laNotaSeHaModificado.collect{
                    if(it) requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun guardar() {
        detalleDeNotaViewModel.guardarCambios(
            binding.tituloNota.text.toString(),
            binding.contenidoNota.text.toString()
        )
    }

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
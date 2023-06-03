package gamaerry.notas.fragmentos.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.R
import gamaerry.notas.databinding.FragmentDetalleDeNotaBinding
import gamaerry.notas.utilidades.mostrarTeclado
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
                }
            }
        }

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
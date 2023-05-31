package gamaerry.notas.fragmentos.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.databinding.FragmentDetalleDeNotaBinding
import gamaerry.notas.utilidades.mostrarTeclado

@AndroidEntryPoint
class DetalleDeNotaFragment : Fragment() {
    private var _binding: FragmentDetalleDeNotaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contenidoNota.mostrarTeclado()
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
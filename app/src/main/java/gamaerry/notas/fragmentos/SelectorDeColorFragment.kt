package gamaerry.notas.fragmentos
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.adaptadores.SelectorDeColorAdapter
import gamaerry.notas.databinding.FragmentSelectorDeColorBinding
import gamaerry.notas.viewmodels.DetalleDeNotaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SelectorDeColorFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectorDeColorBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var selectorDeColorAdapter: SelectorDeColorAdapter
    private val detalleDeNotaViewModel: DetalleDeNotaViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectorDeColorAdapter.setAccionAlHacerClic {
            detalleDeNotaViewModel.actualizarColor(it)
            this.dismiss()
        }
        binding.listaDeColores.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = selectorDeColorAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectorDeColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
    // los binding enlazan las vistas con el codigo
    private var _binding: FragmentSelectorDeColorBinding? = null
    private val binding get() = _binding!!

    // con by activityViewModels() uso la implementacion que me
    // permite usar este objeto dentro del alcance de la actividad
    private val detalleDeNotaViewModel: DetalleDeNotaViewModel by activityViewModels()

    //gracias a la inyeccion de dependencias no tengo que usar el constructor
    @Inject lateinit var selectorDeColorAdapter: SelectorDeColorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // se pasa la accion que ocupara el viewHolder especificada como lateinit
        // en el adapter notese que la accion necesita definirse en este scope
        // pues necesita la referencia del viewModel y del mismo fragment
        selectorDeColorAdapter.accionAlHacerClic = {
            // es aqui donde el viewModel del DetalleDeNotaFragment obtiene el color
            // requerido por el usuario, por ello era necesario que en ambos fragmentos
            // existiera una referencia al un viewModel con un alcance de activity
            detalleDeNotaViewModel.setColor(it)

            // desaparece el SelectorDeColorFragment
            this.dismiss()
        }

        // enlazar el adapter al recyclerView
        binding.listaDeColores.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = selectorDeColorAdapter
        }
    }

    // se sobreescriben estos metodos unicamente para
    // el correcto funcionamiento del nuestro binding
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
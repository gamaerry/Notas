package gamaerry.notas.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gamaerry.notas.adaptadores.ListaDeNotasAdapter
import gamaerry.notas.databinding.FragmentListaDeNotasBinding
import javax.inject.Inject

@AndroidEntryPoint
class ListaDeNotasFragment : Fragment() {
    private var _binding: FragmentListaDeNotasBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var listaDeNotasAdapter: ListaDeNotasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaDeNotasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listaRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listaDeNotasAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
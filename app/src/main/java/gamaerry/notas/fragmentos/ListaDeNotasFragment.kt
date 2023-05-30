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
import gamaerry.notas.databinding.FragmentListaDeNotasBinding
import gamaerry.notas.viewmodels.ListaDeNotasViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListaDeNotasFragment : Fragment() {
    private var _binding: FragmentListaDeNotasBinding? = null
    private val binding get() = _binding!!
    private val listaDeNotasViewModel: ListaDeNotasViewModel by viewModels()
    @Inject
    lateinit var listaDeNotasAdapter: ListaDeNotasAdapter

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
        binding.nuevaNota.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.contenedorPrincipal, DetalleDeNotaFragment())
                addToBackStack("detallesProfesionales")
                commit()
            }
        }
        binding.listaRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listaDeNotasAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                listaDeNotasViewModel.listaDeNotas.collect { listaDeNotasAdapter.submitList(it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
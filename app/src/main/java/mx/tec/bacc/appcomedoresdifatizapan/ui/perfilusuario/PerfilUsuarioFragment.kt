package mx.tec.bacc.appcomedoresdifatizapan.ui.perfilusuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentPerfilUsuarioBinding
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente.Usuario

class PerfilUsuarioFragment : Fragment() {

private var _binding: FragmentPerfilUsuarioBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val perfilUsuarioViewModel =
            ViewModelProvider(this).get(PerfilUsuarioViewModel::class.java)

    _binding = FragmentPerfilUsuarioBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.txtPerfilUsuario
    perfilUsuarioViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
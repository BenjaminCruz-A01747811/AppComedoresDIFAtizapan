package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.tec.bacc.appcomedoresdifatizapan.R
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioAppBinding

class InicioAppFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentInicioAppBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {

        _binding = FragmentInicioAppBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val btnRegistro:Button = binding.btnRegistro
        val btnInicioSesion:Button = binding.btnInicioSesion

        // Configura un OnClickListener para el botón btnRegistro
        btnRegistro.setOnClickListener {
            // Navega al fragmento de RegistroClienteFragment
            findNavController().navigate(R.id.nav_int_registro_cliente)
        }

        // Configura un OnClickListener para el botón btnInicioSesion
        btnInicioSesion.setOnClickListener {
            // Navega al fragmento de InicioClienteFragment
            findNavController().navigate(R.id.nav_int_inicio_cliente)
        }
        return root

    }
}

package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioColaboradorBinding

class InicioColabFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentInicioColaboradorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = FragmentInicioColaboradorBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val etCorreo = binding.edtCorreo
        val etContra = binding.edtContra2
        val btnIngresar = binding.btnIngresar2

        btnIngresar.setOnClickListener{
            val correoString = etCorreo.text.toString().trim()
            val contraString = etContra.text.toString().trim()
        }

        return root
    }
}
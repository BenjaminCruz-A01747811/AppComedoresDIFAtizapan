package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import mx.tec.bacc.appcomedoresdifatizapan.R
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioColaboradorBinding
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentRegistroColaboradorBinding

class RegistroColabFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentRegistroColaboradorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegistroColaboradorBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val etNombre = binding.edtNombre
        val etApellidos = binding.edtApellido
        val etContra = binding.edtContra3
        val btnRegColab = binding.btnRegColab

        btnRegColab.setOnClickListener{
            val correoString = etNombre.text.toString().trim()
            val nombreString = etApellidos.text.toString().trim()
            val contraString = etContra.text.toString().trim()
        }

        // Cambiar a Inicio Colaborador
        val tvInicColab = view?.findViewById<TextView>(R.id.nav_int_inicio_colab)
        tvInicColab?.setOnClickListener {
            // Iniciar el nuevo fragmento
            startActivity(Intent(activity, FragmentInicioColaboradorBinding::class.java))
        }

        return root
    }


}
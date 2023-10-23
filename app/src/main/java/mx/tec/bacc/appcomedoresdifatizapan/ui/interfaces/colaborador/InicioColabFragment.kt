package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.tec.bacc.appcomedoresdifatizapan.R
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioColaboradorBinding
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentRegistroColaboradorBinding
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador.Colaborador
import com.google.firebase.firestore.FirebaseFirestore

object colaboradorIni{
    var nombre: String = ""
    var apellidos: String = ""
    var fechaNac: String = ""
    var sexo: String = ""
    var contraseña: String = ""
    var correo: String = ""
    var notNull: Boolean = true


}

class InicioColabFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentInicioColaboradorBinding? = null
    val db = FirebaseFirestore.getInstance()

    fun searchUsuarioByEmail(targetEmail: String, targetContraseña: String, callback: (Colaborador?) -> Unit) {
        val usuariosCollection = db.collection("colaboradores")

        try {
            usuariosCollection.whereEqualTo("correo", targetEmail) // Change to email
                .whereEqualTo("contraseña", targetContraseña)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val data = document.data
                        if (data != null) {
                            val colaborador = Colaborador(
                                data["nombre"] as String,
                                data["apellidos"] as String,
                                data["fechaNac"] as String,
                                data["sexo"] as String,
                                data["contraseña"] as String,
                                data["correo"] as String // Retrieve email
                            )
                            callback(colaborador)
                            return@addOnSuccessListener
                        }
                    }

                    // If no matching document is found, return null
                    callback(null)
                }
                .addOnFailureListener { exception ->
                    // Handle the exception here if necessary
                    callback(null)
                }
        } catch (e: Exception) {
            callback(null)
        }
    }




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
        val tvRegColab = binding.tvRegColab

        btnIngresar.setOnClickListener{
            val correoString = etCorreo.text.toString().trim()
            val contraString = etContra.text.toString().trim()

            val foundColaborador = searchUsuarioByEmail(correoString, contraString) { colaborador ->
                // Handle the result here
                if (colaborador != null) {
                    // A matching user was found
                    // Use the `colaborador` object
                    colaboradorIni.nombre = colaborador.nombre
                    colaboradorIni.apellidos = colaborador.apellidos
                    colaboradorIni.correo = colaborador.correo // Set email
                    colaboradorIni.fechaNac = colaborador.fechaNac
                    colaboradorIni.sexo = colaborador.sexo
                    colaboradorIni.contraseña = colaborador.contraseña
                    colaboradorIni.notNull = true
                } else {
                    // No matching user found
                    println("User not found.")
                    colaboradorIni.notNull = false
                }
            }
        }

        // Cambiar a Registro Colaborador
        tvRegColab.setOnClickListener {
            findNavController().navigate(R.id.nav_int_registro_cliente)
        }




        return root
    }
}
package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.tec.bacc.appcomedoresdifatizapan.R
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioColaboradorBinding
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentRegistroColaboradorBinding
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador.Colaborador
import com.google.firebase.firestore.FirebaseFirestore
import java.time.format.DateTimeFormatter

class RegistroColabFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentRegistroColaboradorBinding? = null
    private val db = FirebaseFirestore.getInstance()

    fun getCurrentDate(): String {
        val currentDate = java.time.LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return currentDate.format(formatter)
    }

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
        val tvInicColab = binding.tvInicioColab

        val currentDate = getCurrentDate()
        val usuariosCollection = db.collection("colaboradores")

        btnRegColab.setOnClickListener{
            val correoString = etNombre.text.toString().trim()
            val nombreString = etApellidos.text.toString().trim()
            val contraString = etContra.text.toString().trim()

            if (nombreString.isEmpty() || correoString.isEmpty() || contraString.isEmpty() ) {
                Toast.makeText(context, "Complete los datos", Toast.LENGTH_SHORT).show()
            } else {
                // Check if the "curp" is duplicated before adding the document
                checkCurpDuplication(correoString) {
                    if (it) {
                        // Handle the case when "curp" is duplicated
                        println("Duplicated CURP: $correoString. Cannot add the document.")
                    } else {
                        // "curp" is not duplicated, so add the document
                        val nuevoUsuario = hashMapOf(
                            "nombre" to nombreString,
                            "correo" to correoString,
                            "contraseña" to contraString,
                            "fechaRegistro" to currentDate // Add registration date
                        )

                        usuariosCollection.add(nuevoUsuario)
                            .addOnSuccessListener {
                                // Documento creado con éxito
                                println("Documento creado con éxito")
                                // Redirect to the desired screen or show a success message.
                            }
                            .addOnFailureListener { e ->
                                // Error al crear el documento
                                println("Error al crear el documento: $e")
                                // Handle the error, show an error message, etc.
                            }
                    }
                }
            }

        }

        // Cambiar a Inicio Colaborador
        tvInicColab.setOnClickListener {
            // Iniciar el nuevo fragmento
            findNavController().navigate(R.id.nav_int_inicio_colab)
        }

        return root
    }

    private fun checkCurpDuplication(newCurp: String, callback: (Boolean) -> Unit) {
        val collectionRef = db.collection("usuarios")

        collectionRef.whereEqualTo("correo", newCurp).get()
            .addOnSuccessListener { querySnapshot ->
                callback(!querySnapshot.isEmpty)
            }
            .addOnFailureListener { e ->
                callback(false)
                println("Error al verificar duplicado CURP: $e")
            }
    }

}
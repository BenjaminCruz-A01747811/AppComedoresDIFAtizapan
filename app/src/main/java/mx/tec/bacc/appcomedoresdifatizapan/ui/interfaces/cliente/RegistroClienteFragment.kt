package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mx.tec.bacc.appcomedoresdifatizapan.R
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentRegistroClienteBinding
import java.time.format.DateTimeFormatter
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente.Usuario

object usuarioReg {
    var sexo: String = ""
    var condicion: String = ""
}

class RegistroClienteFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentRegistroClienteBinding? = null
    private val db = FirebaseFirestore.getInstance()

    fun addWordWithComma(wordToAdd: String) {
        if (usuarioReg.condicion.isEmpty()) {
            usuarioReg.condicion = wordToAdd
        } else {
            usuarioReg.condicion += ", $wordToAdd"
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.Masculino ->
                    if (checked) {
                        usuarioReg.sexo = "Masculino"
                    }
                R.id.Femenino ->
                    if (checked) {
                        usuarioReg.sexo = "Femenino"
                    }
            }
        }
    }
    fun removeWordFromString(inputString: String, wordToRemove: String): String {
        val words = inputString.split(",").map { it.trim() }
        val filteredWords = words.filter { it != wordToRemove }
        return filteredWords.joinToString(", ")
    }


    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.conEmbarazo -> {
                    if (checked) {
                        addWordWithComma("Embarazada")
                    } else {
                        removeWordFromString(usuarioReg.condicion, "Embarazada")
                    }
                }
                R.id.conCronica -> {
                    if (checked) {
                        addWordWithComma("Enfermedad_Cronica")
                    } else {
                        removeWordFromString(usuarioReg.condicion,"Enfermedad_Cronica")
                    }
                }
                R.id.conDiscapacidad -> {
                    if (checked) {
                        addWordWithComma("Discapacidad")
                    } else {
                        removeWordFromString(usuarioReg.condicion,"Discapacidad")
                    }
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {

        _binding = FragmentRegistroClienteBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val btnRegistrarCliente: Button = binding.btnIngresar1
        val nombre = binding.nombreET
        val apellidos = binding.apellidosET
        val fecha = binding.fechaET
        val curp = binding.curpET
        val password = binding.passwordET
        val sexo = usuarioReg.sexo
        val condicion = usuarioReg.condicion
        val tvInicCliente = binding.tvInicioCliente

        fun getCurrentDate(): String {
            val currentDate = java.time.LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return currentDate.format(formatter)
        }


        val currentDate = getCurrentDate()
        val usuariosCollection = db.collection("usuarios")


        btnRegistrarCliente.setOnClickListener {
            val nombreString = nombre.text.toString().trim()
            val apellidoString = apellidos.text.toString().trim()
            val fechaNac = fecha.text.toString().trim()
            val curpString = curp.text.toString().trim()
            val passwordString = password.text.toString().trim()
            val sexoString = usuarioReg.sexo
            val condicionString = usuarioReg.condicion


            if (nombreString.isEmpty() || apellidoString.isEmpty() || fechaNac.isEmpty() || curpString.isEmpty() || passwordString.isEmpty()) {
                Toast.makeText(context, "Complete los datos", Toast.LENGTH_SHORT).show()
            } else {
                // Check if the "curp" is duplicated before adding the document
                checkCurpDuplication(curpString) {
                    if (it) {
                        // Handle the case when "curp" is duplicated
                        println("Duplicated CURP: $curpString. Cannot add the document.")
                    } else {
                        // "curp" is not duplicated, so add the document
                        val nuevoUsuario = hashMapOf(
                            "nombre" to nombreString,
                            "apellidos" to apellidoString,
                            "curp" to curpString,
                            "fechaNac" to fechaNac,
                            "sexo" to sexoString,
                            "condicion" to condicionString,
                            "fechaRegistro" to currentDate, // Add registration date
                            "contraseña" to passwordString
                        )

                        usuariosCollection.add(nuevoUsuario)
                            .addOnSuccessListener {
                                // Documento creado con éxito
                                println("Documento creado con éxito")
                                // Redirect to the desired screen or show a success message.
                                Toast.makeText(context, "Usuario creado con exito", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                // Error al crear el documento
                                println("Error al crear el documento: $e")
                                // Handle the error, show an error message, etc.
                                Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        }

        // Cambiar a Inicio Cliente
        tvInicCliente.setOnClickListener {
            findNavController().navigate(R.id.nav_int_inicio_cliente)
        }

        return root
    }

    private fun checkCurpDuplication(newCurp: String, callback: (Boolean) -> Unit) {
        val collectionRef = db.collection("usuarios")

        collectionRef.whereEqualTo("curp", newCurp).get()
            .addOnSuccessListener { querySnapshot ->
                callback(!querySnapshot.isEmpty)
            }
            .addOnFailureListener { e ->
                callback(false)
                println("Error al verificar duplicado CURP: $e")
            }
    }
}
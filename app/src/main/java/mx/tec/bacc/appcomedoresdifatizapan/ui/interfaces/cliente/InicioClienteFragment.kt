package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioClienteBinding
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente.Usuario

class InicioClienteFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentInicioClienteBinding? = null
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    fun searchUsuarioByCurp(targetCurp: String, callback: (Usuario?) -> Unit) {
        val usuariosCollection = db.collection("usuarios")

        try {
            usuariosCollection.whereEqualTo("curp", targetCurp).get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val data = document.data
                        if (data != null) {
                            val usuario = Usuario(
                                data["nombre"] as String,
                                data["apellidos"] as String,
                                data["curp"] as String,
                                data["fechaNac"] as String,
                                data["sexo"] as String,
                                data["condicion"] as String,
                                data["contraseña"] as String
                            )
                            callback(usuario)
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
    ): View {

        _binding = FragmentInicioClienteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val etCurp = binding.edtCurp
        val etContra = binding.edtContra
        val bnIngreso: Button = binding.btnIngresar1

        bnIngreso.setOnClickListener {
            val curpString = etCurp.text.toString().trim()
            val passwordString = etContra.text.toString().trim()

            fun run() {
                val targetCurp = "CURP12345"

                val foundUsuario = searchUsuarioByCurp(targetCurp) { usuario ->
                    // Handle the result here
                    if (usuario != null) {
                        // A matching user was found
                        // Use the `usuario` object
                        println("User found: ${usuario.nombre} ${usuario.apellidos}")
                    } else {
                        // No matching user found
                        println("User not found.")
                    }
                }

                if (foundUsuario != null) {
                    println("Found User: ${foundUsuario.nombre} ${foundUsuario.apellidos}")

                } else {
                    println("No user with the specified CURP found.")
                }
            }

            run()




        }


        return root
    }

    public override fun onStart() {
        super.onStart()

        updateUI()
    }

    fun updateUI(user: Usuario?) {
        if (user != null) {
            // El usuario está autenticado, puedes realizar acciones específicas para un usuario autenticado.
            // Por ejemplo, mostrar su nombre en la interfaz de usuario.
            val curpUsuario = user.curp
            val contraUsuario = user.contrasena

            // Actualiza la interfaz de usuario con la información del usuario
            // (puedes hacer esto de acuerdo a tus necesidades)
            // Por ejemplo, puedes mostrar el nombre del usuario en un TextView.
            nombreTextView.text = "Nombre: $nombreUsuario"
            emailTextView.text = "Email: $emailUsuario"

            // Además, puedes habilitar o deshabilitar funcionalidades específicas para usuarios autenticados.

        } else {
            // El usuario no está autenticado, puedes realizar acciones específicas para un usuario no autenticado.
            // Por ejemplo, ocultar elementos de la interfaz de usuario que requieren autenticación.

            // Puedes mostrar un mensaje para alentar al usuario a iniciar sesión o registrarse.
            nombreTextView.text = "Usuario no autenticado"
            emailTextView.text = ""

            // También puedes deshabilitar funcionalidades que requieran autenticación.
        }
    }

}


package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioClienteBinding

class InicioClienteFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentInicioClienteBinding? = null
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    data class Usuario(
        val nombre: String,
        val apellidos: String,
        val curp: String,
        val fechaNac: String,
        val sexo: String,
        val condicion: String,
        val contrasena: String
    )

    fun searchUsuarioByCurp(targetCurp: String): Usuario? {
        val usuariosCollection = db.collection("usuarios")

        try {
            val querySnapshot = usuariosCollection.whereEqualTo("curp", targetCurp).get().await()

            for (document in querySnapshot.documents) {
                val data = document.data
                if (data != null) {
                    return Usuario(
                        data["nombre"] as String,
                        data["apellidos"] as String,
                        data["curp"] as String,
                        data["fechaNac"] as String,
                        data["sexo"] as String,
                        data["condicion"] as String,
                        data["contraseña"] as String
                    )
                }
            }

            // If no matching document is found, return null
            return null
        } catch (e: Exception) {
            return null
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

           /* suspend fun run() {
                val targetCurp = "CURP12345"

                val foundUsuario = searchUsuarioByCurp(targetCurp)

                if (foundUsuario != null) {
                    println("Found User: ${foundUsuario.nombre} ${foundUsuario.apellidos}")

                } else {
                    println("No user with the specified CURP found.")
                }
            }

            runBlocking { run() } */

            searchUsuarioByCurp(curpString)


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


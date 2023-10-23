package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import mx.tec.bacc.appcomedoresdifatizapan.R
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentInicioClienteBinding
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentRegistroClienteBinding
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentRegistroColaboradorBinding
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente.Usuario

class InicioClienteFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentInicioClienteBinding? = null
    val db = FirebaseFirestore.getInstance()

    object usuarioIni {
        var nombre: String = ""
        var apellidos: String = ""
        var curp: String = ""
        var fechaNac: String = ""
        var sexo: String = ""
        var condicion: String = ""
        var notNull: Boolean = true
        var contraseña: String = ""
    }

    fun searchUsuarioByCurp(targetCurp: String, targetContraseña: String, callback: (Usuario?) -> Unit) {
        val usuariosCollection = db.collection("usuarios")

        try {
            usuariosCollection.whereEqualTo("curp", targetCurp)
                .whereEqualTo("contraseña", targetContraseña)
                .get()
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
        // Obtener el TextView
        val tvRegCliente = binding.tvRegCliente

        bnIngreso.setOnClickListener {
            val curpString = etCurp.text.toString().trim()
            val passwordString = etContra.text.toString().trim()

            searchUsuarioByCurp(curpString, passwordString) { usuario ->
                    // Handle the result here
                    if (usuario != null) {
                        // A matching user was found
                        // Use the `usuario` object
                        usuarioIni.nombre = usuario.nombre
                        usuarioIni.apellidos = usuario.apellidos
                        usuarioIni.curp = usuario.curp
                        usuarioIni.fechaNac = usuario.fechaNac
                        usuarioIni.sexo = usuario.sexo
                        usuarioIni.condicion = usuario.condicion
                        usuarioIni.contraseña = usuario.contrasena
                        usuarioIni.notNull = true

                        findNavController().navigate(R.id.nav_asist_qr)
                    } else {
                        // No matching user found
                        println("User not found.")
                        usuarioIni.notNull = false
                        Toast.makeText(context, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Cambiar a Registro Cliente
        tvRegCliente.setOnClickListener {
            findNavController().navigate(R.id.nav_int_registro_cliente)
        }

        return root
    }



    fun updateUI() {
        if (usuarioIni.notNull) {
            // El usuario está autenticado, puedes realizar acciones específicas para un usuario autenticado.
            // Por ejemplo, mostrar su CURP en la interfaz de usuario.
            val curpUsuario = usuarioIni.curp
            val contraUsuario = usuarioIni.contraseña

            // Actualiza la interfaz de usuario con la información del usuario
            // (puedes hacer esto de acuerdo a tus necesidades)
            // Por ejemplo, puedes mostrar el CURP del usuario en un TextView.


            // Además, puedes habilitar o deshabilitar funcionalidades específicas para usuarios autenticados.

        } else {
            // El usuario no está autenticado, puedesok realizar acciones específicas para un usuario no autenticado.
            // Por ejemplo, ocultar elementos de la interfaz de usuario que requieren autenticación.

            // Puedes mostrar un mensaje para alentar al usuario a iniciar sesión o registrarse.


            // También puedes deshabilitar funcionalidades que requieran autenticación.
        }
    }

    public override fun onStart() {
        super.onStart()

        updateUI()
    }
}


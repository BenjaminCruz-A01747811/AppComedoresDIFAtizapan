package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces

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
        val condicion: String
    )

    suspend fun searchUsuarioByCurp(targetCurp: String): Usuario? {
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
                        data["condicion"] as String
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

        _binding = FragmentInicioClienteBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val etCurp = binding.edtCurp
        val etContra = binding.edtContra
        val bnIngreso:Button = binding.btnIngresar1

        bnIngreso.setOnClickListener{
            val curpString = etCurp.text.toString().trim()
            val passwordString = etContra.text.toString().trim()

            suspend fun run(){val targetCurp = "CURP12345"

                val foundUsuario = searchUsuarioByCurp(targetCurp)

                if (foundUsuario != null) {
                    println("Found User: ${foundUsuario.nombre} ${foundUsuario.apellidos}")
                } else {
                    println("No user with the specified CURP found.")
                }}

            runBlocking{run()}



        }


        return root
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }


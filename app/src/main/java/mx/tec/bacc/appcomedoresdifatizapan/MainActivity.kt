package mx.tec.bacc.appcomedoresdifatizapan
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import mx.tec.bacc.appcomedoresdifatizapan.databinding.ActivityMainBinding
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.btnSoporte.setOnClickListener { view ->
            val options = arrayOf("Correo electrónico", "WhatsApp")
            AlertDialog.Builder(this)
                .setTitle("Selecciona una opción")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> { // Opción "Correo electrónico"
                            val email = "a01747811@tec.mx"
                            val subject = "SOPORTE DE LA APP"
                            val message = "Escribe aquí tu consulta o problema."

                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "message/rfc822"
                            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                            intent.putExtra(Intent.EXTRA_TEXT, message)

                            try {
                                startActivity(Intent.createChooser(intent, "Elige una aplicación de correo electrónico"))
                            } catch (ex: android.content.ActivityNotFoundException) {
                                // Manejar el caso en el que no hay aplicaciones de correo electrónico disponibles.
                                Toast.makeText(this, "No se encontró una aplicación de correo electrónico.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        1 -> { // Opción "WhatsApp"
                            val phoneNumber = ""  // Reemplaza con el número de teléfono al que deseas enviar el mensaje.
                            val message = "Hola, necesito soporte para la aplicación."

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")

                            if (intent.resolveActivity(packageManager) != null) {
                                startActivity(intent)
                            } else {
                                // Manejar el caso en el que no se pueda abrir WhatsApp.
                                Toast.makeText(this, "No se encontró la aplicación de WhatsApp.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                .show()
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_asist_qr, R.id.nav_menu_dia, R.id.nav_perfil_usuario, R.id.nav_mapa_comedores), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
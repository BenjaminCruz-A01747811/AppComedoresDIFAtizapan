package mx.tec.bacc.appcomedoresdifatizapan.ui.qr

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentAsistQrBinding
import java.util.Calendar
import java.util.Locale
import mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente.Usuario



class AsistQrFragment : Fragment() {

  private val db = FirebaseFirestore.getInstance()

  private var _binding: FragmentAsistQrBinding? = null

  private val binding get() = _binding!!

  // Declarar una referencia a la base de datos
  private lateinit var databaseReference: DatabaseReference



  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    val asistQrViewModel =
      ViewModelProvider(this).get(AsistQrViewModel::class.java)

    _binding = FragmentAsistQrBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.txtQr
    asistQrViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }

    //TextView para mostrar el usuario actual
    val editText: EditText = binding.edtUserName

    // Crear una nueva colección llamada "usuarios"
    val usuariosCollection = db.collection("usuarios")

    // Crear un documento con datos en la colección "usuarios"
    val primerDocumento = hashMapOf(
      "nombre" to "Lalo",
      "apellidos" to "Cruz",
      "curp" to "L4L0NG4N1ZA",
      "fechaNac" to "18/12/1234",
      "sexo" to "Hombre",
      "condicion" to "Discapacidad"
    )

    usuariosCollection
      //.document("primerDocumento")  // Puedes definir un ID de documento o dejarlo vacío para que se genere automáticamente
      .add(primerDocumento)
      .addOnSuccessListener {
        // Documento creado con éxito
        println("Documento creado con éxito")
      }
      .addOnFailureListener { e ->
        // Error al crear el documento
        println("Error al crear el documento")
      }

    /*
    //Inicializar la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().reference.child("usuarios").child("usuarios_comedor")

    //Agregar un ValueEventListener para escuchar cambios en la referencia
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

    //Obtener el valor del nombre del usuario
                val nombreUsuario = dataSnapshot.child("nombre").value.toString()

    //Mostrar el nombre de lusuario en el TextView
                editText.text = nombreUsuario

    //This method is called once with the initial value and again
    //wheneverdataatthislocationisupdated.
                val value = dataSnapshot.getValue<String>()

                Log.d("AsistQrFragment", "Valueis:+$value")
            }

            override fun onCancelled(databaseError: DatabaseError) {
    //Manejar errores si es necesario
                Log.w("AsistQrFragment", "Failedtoreadvalue.", databaseError.toException())

            }
        })
    */



    // Fecha completa
    val fechaCompleta: TextView = binding.tvFecha

    // Establecer la Locale en español
    val spanishLocale = Locale("es", "ES")
    val calendar = Calendar.getInstance(spanishLocale)
    val dayOfWeek = primeraLetraMayuscula(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, spanishLocale))
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, spanishLocale)
    val year = calendar.get(Calendar.YEAR)

    val fechaCompletaString = "$dayOfWeek $dayOfMonth de $month del $year"
    fechaCompleta.text = fechaCompletaString

    val imageView: ImageView = binding.ivQR
    val btnGenerar: Button = binding.btnGenQR
    //val editText: EditText = binding.editTextText

    // Concatena el nombre del comensal con la fecha
    //val codigoQrData = "$textViewUsuario - $fechaFormateada"
    val codigoQrData = "$editText - $fechaCompletaString"

    //Botón Generar QR
    btnGenerar.setOnClickListener {

      db.collection("usuarios").document("usuarios_comedor").get().addOnSuccessListener {
        editText.setText(it.get("userID") as String?)
      }
      //val codigoQrData = "$editText - $fechaFormateada"

      try {
        val barcoEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcoEncoder.encodeBitmap(
          codigoQrData,
          BarcodeFormat.QR_CODE,
          750,
          750
        )
        imageView.setImageBitmap(bitmap)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

    return root

  }
    
override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

  private fun primeraLetraMayuscula(input: String): String {
    return input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1)
  }

}
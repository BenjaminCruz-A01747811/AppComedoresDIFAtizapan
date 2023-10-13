package mx.tec.bacc.appcomedoresdifatizapan.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentAsistQrBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class AsistQrFragment : Fragment() {

private var _binding: FragmentAsistQrBinding? = null

  private val binding get() = _binding!!

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
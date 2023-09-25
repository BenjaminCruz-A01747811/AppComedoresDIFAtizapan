package mx.tec.bacc.appcomedoresdifatizapan.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentAsistQrBinding

class AsistQrFragment : Fragment() {

private var _binding: FragmentAsistQrBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
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
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
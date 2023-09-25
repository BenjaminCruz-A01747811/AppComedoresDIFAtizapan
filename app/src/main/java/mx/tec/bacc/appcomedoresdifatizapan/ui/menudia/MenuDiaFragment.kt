package mx.tec.bacc.appcomedoresdifatizapan.ui.menudia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentMenuDiaBinding

class MenuDiaFragment : Fragment() {

    private var _binding: FragmentMenuDiaBinding? = null
      // Esta propiedad sólo es válida entre onCreateView y onDestroyView.
      private val binding get() = _binding!!

      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
      ): View {
        val menuDiaViewModel= ViewModelProvider(this).get(MenuDiaViewModel::class.java)

        _binding = FragmentMenuDiaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.txtMenuDia
        menuDiaViewModel.text.observe(viewLifecycleOwner) {
          textView.text = it
        }
        return root
      }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}
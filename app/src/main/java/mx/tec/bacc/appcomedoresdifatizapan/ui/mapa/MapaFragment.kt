package mx.tec.bacc.appcomedoresdifatizapan.ui.mapa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentMapaComedoresBinding
import android.Manifest

class MapaFragment : Fragment() {

    private var _binding: FragmentMapaComedoresBinding? = null
    // Esta propiedad sólo es válida entre onCreateView y onDestroyView.
     private val binding get() = _binding!!

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         val mapaViewModel=
             ViewModelProvider(this).get(MapaViewModel::class.java  )

         _binding = FragmentMapaComedoresBinding.inflate(inflater, container, false)
         val root: View = binding.root


         return TODO("Provide the return value")
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
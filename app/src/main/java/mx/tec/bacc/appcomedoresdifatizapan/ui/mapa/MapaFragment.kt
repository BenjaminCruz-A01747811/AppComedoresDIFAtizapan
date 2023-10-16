/*package mx.tec.bacc.appcomedoresdifatizapan.ui.mapa

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


}*/

package mx.tec.bacc.appcomedoresdifatizapan.ui.mapa

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tec.bacc.appcomedoresdifatizapan.databinding.FragmentMapaComedoresBinding
import android.Manifest
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import mx.tec.bacc.appcomedoresdifatizapan.R

class MapaFragment : Fragment() {

    private var _binding: FragmentMapaComedoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapaViewModel = ViewModelProvider(this).get(MapaViewModel::class.java)

        _binding = FragmentMapaComedoresBinding.inflate(inflater, container, false)
        val root: View = binding.root

        webView = root.findViewById(R.id.webview)
        setupWebView()

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Carga la página de Google Maps
        webView.clearCache(true)
        webView.loadUrl("https://www.google.com/maps/d/viewer?mid=16p4XUJ3OiJqezpHleTAKGHy4Ti_8rYc&ll=19.575418665693352%2C-99.24592264703536&z=13")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        // Configurar permisos de geolocalización
        webView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?
            ) {
                callback?.invoke(origin, true, false)
            }
        }

        // Solicitar permiso de ubicación en tiempo de ejecución
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }
}

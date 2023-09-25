package mx.tec.bacc.appcomedoresdifatizapan.ui.mapa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapaViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pantalla donde se desplegará un Mapa de los comedores"
    }
    val text: LiveData<String> = _text
}
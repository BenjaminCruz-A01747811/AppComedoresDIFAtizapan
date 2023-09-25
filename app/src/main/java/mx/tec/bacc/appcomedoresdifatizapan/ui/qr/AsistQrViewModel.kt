package mx.tec.bacc.appcomedoresdifatizapan.ui.qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AsistQrViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pantalla para el Código QR"
    }
    val text: LiveData<String> = _text
}
package mx.tec.bacc.appcomedoresdifatizapan.ui.menudia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuDiaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pantalla para desplegar el Menú del Día"
    }
    val text: LiveData<String> = _text
}
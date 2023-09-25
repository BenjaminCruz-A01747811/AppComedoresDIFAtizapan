package mx.tec.bacc.appcomedoresdifatizapan.ui.perfilusuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PerfilUsuarioViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pantalla donde despliega el Perfil de Usuario"
    }
    val text: LiveData<String> = _text
}
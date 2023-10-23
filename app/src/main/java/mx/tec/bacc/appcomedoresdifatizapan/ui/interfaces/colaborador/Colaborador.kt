package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.colaborador

data class Colaborador(
    val nombre: String,
    val apellidos: String,
    val fechaNac: String,
    val sexo: String,
    var contraseña: String = "",
    var correo: String

)

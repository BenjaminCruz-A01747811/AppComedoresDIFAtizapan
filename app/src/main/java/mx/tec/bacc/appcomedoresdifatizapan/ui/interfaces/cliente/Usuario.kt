package mx.tec.bacc.appcomedoresdifatizapan.ui.interfaces.cliente

data class Usuario(
    val nombre: String,
    val apellidos: String,
    val curp: String,
    val fechaNac: String,
    val sexo: String,
    val condicion: String,
    val contrasena: String
)

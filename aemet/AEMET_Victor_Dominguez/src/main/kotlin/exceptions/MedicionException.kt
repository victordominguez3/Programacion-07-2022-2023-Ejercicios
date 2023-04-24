package exceptions

import java.lang.Exception

sealed class MedicionException(message: String): Exception(message)

class MedicionNoValidaException(message: String): MedicionException("Medición no válida: $message")
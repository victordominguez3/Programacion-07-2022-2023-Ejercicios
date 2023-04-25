package exceptions

sealed class PersonaException(message: String): Exception(message)
class ProfesorNoValidoException(message: String): PersonaException("Profesor no válido: $message")
class AlumnoNoValidoException(message: String): PersonaException("Alumno no válido: $message")
class PersonaNoValidaException(message: String): PersonaException("Persona no válida: $message")
class AlumnoNoEncontradoException(message: String): PersonaException("Sin alumnos: $message")
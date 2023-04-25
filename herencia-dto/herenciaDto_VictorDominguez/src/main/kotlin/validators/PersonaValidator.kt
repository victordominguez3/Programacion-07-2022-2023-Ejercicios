package validators

import exceptions.AlumnoNoValidoException
import exceptions.ProfesorNoValidoException
import models.Alumno
import models.Profesor
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Alumno.validar() {
    logger.debug { "Validar: $this" }
    require(nombre.isNotBlank()) { throw AlumnoNoValidoException("El alumno no puede tener un nombre vacío") }
    require(edad in 18..40) { throw AlumnoNoValidoException("La edad del alumno debe ser entre 18 y 40") }
}

fun Profesor.validar() {
    logger.debug { "Validar: $this" }
    require(nombre.isNotBlank()) { throw ProfesorNoValidoException("El profesor no puede tener un nombre vacío") }
    require(modulo.isNotBlank()) { throw ProfesorNoValidoException("El profesor debe tener un módulo asignado") }
}
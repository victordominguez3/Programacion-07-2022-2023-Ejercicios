package factories

import MORADO
import RESET
import models.Alumno
import models.Persona
import models.Profesor
import mu.KotlinLogging
import validators.validar

private val logger = KotlinLogging.logger {}

object PersonasFactory {
    val nombres = listOf(
        "Lucas",
        "Sonia",
        "Pedro",
        "Jose Miguel",
        "Rebeca",
        "Natalia",
        "Lucía",
        "Fernando",
        "Víctor",
        "Juan",
        "Hernán",
        "Nuria",
        "Pablo",
        "Rosa",
        "Pedro"
    )
    val modulos = listOf(
        "Programación",
        "Entornos"
    )
    val rangoEdad = 18..40

    fun crearClaseEntera(): List<Persona> {
        logger.debug { "${MORADO}Factory$RESET -> Creando clase de personas" }
        val lista = mutableListOf<Persona>()
        repeat(2) {lista.add(crearProfesor())}
        repeat(18) {lista.add(crearAlumno())}
        return lista
    }

    private fun crearProfesor() = Profesor(
        nombres.random(),
        modulos.random()
    ).also { it.validar() }.also { logger.debug { "${MORADO}Factory$RESET -> Creando profesor" } }

    private fun crearAlumno() = Alumno(
        nombres.random(),
        rangoEdad.random()
    ).also { it.validar() }.also { logger.debug { "${MORADO}Factory$RESET -> Creando alumno" } }
}
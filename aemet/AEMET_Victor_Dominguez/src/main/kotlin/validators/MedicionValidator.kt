package validators

import exceptions.MedicionNoValidaException
import models.Medicion
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Medicion.validar() {
    logger.debug { "Validando: $this" }
    require(lugar.isNotBlank()) { throw MedicionNoValidaException("La medición debe tener un lugar asignado") }
    require(provincia.isNotBlank()) { throw MedicionNoValidaException("La medición debe tener una provincia asignada") }
}
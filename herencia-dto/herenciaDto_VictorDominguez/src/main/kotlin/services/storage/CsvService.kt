package services.storage

import MORADO
import RESET
import dto.PersonaDto
import mappers.toPersona
import mappers.toPersonaDtoList
import models.Alumno
import models.Persona
import models.Profesor
import mu.KotlinLogging
import java.io.File
import kotlin.math.log

private val logger = KotlinLogging.logger {}

object CsvService: PersonasStorageService {

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}personasCsv.csv"
    private val fichero = File(path)

    override fun exportar(items: List<Persona>) {
        logger.debug { "${MORADO}CsvService$RESET -> Exportando a CSV" }
        fichero.writeText("Tipo;Nombre;Edad;Modulo\n")
        items.toPersonaDtoList().personas.forEach {
            fichero.appendText("${it.tipo};${it.nombre};${it.edad};${it.modulo}\n")
        }
    }

    override fun importar(): List<Persona> {
        logger.debug { "${MORADO}CsvService$RESET -> Importando desde CSV" }
        return fichero.readLines()
            .drop(1)
            .map { linea -> linea.split(";") }
            .map { columnas ->
                PersonaDto(
                    columnas[0],
                    columnas[1],
                    columnas[2],
                    columnas[3]
                ).toPersona()
            }
    }
}
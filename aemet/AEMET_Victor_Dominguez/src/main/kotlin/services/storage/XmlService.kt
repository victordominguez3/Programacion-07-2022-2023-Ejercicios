package services.storage

import MORADO
import RESET
import mappers.toMedicionesList
import mappers.toMedicionesListDto
import models.Medicion
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

private val logger = KotlinLogging.logger {}

object XmlService: MedicionStorageService {

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompletoXml.xml"
    private val fichero = File(path)
    private val serializer = Persister()

    override fun exportar(items: List<Medicion>) {
        logger.debug { "${MORADO}XmlService$RESET -> Exportando a XML" }
        serializer.write(items.toMedicionesListDto(), fichero)
    }

    override fun importar(): List<Medicion> {
        logger.debug { "${MORADO}XmlService$RESET -> Importando desde XML" }
        return serializer.read(dto.MedicionesListDto::class.java, fichero).toMedicionesList()
    }
}
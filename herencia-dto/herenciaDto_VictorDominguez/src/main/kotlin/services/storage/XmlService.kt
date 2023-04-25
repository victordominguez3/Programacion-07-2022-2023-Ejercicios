package services.storage

import MORADO
import RESET
import dto.PersonaDto
import dto.PersonasListDto
import mappers.toPersonaDtoList
import mappers.toPersonaList
import models.Persona
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

private val logger = KotlinLogging.logger {}

object XmlService: PersonasStorageService {

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}personasXml.xml"
    private val fichero = File(path)
    val serializer = Persister()

    override fun exportar(items: List<Persona>) {
        logger.debug { "${MORADO}XmlService$RESET -> Exportando a XML" }
        serializer.write(items.toPersonaDtoList(), fichero)
    }

    override fun importar(): List<Persona> {
        logger.debug { "${MORADO}XmlService$RESET -> Importando desde XML" }
        return serializer.read(PersonasListDto::class.java, fichero).toPersonaList()
    }
}
package services.storage

import MORADO
import RESET
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dto.PersonaDto
import dto.PersonasListDto
import mappers.toPersona
import mappers.toPersonaDto
import mappers.toPersonaDtoList
import mappers.toPersonaList
import models.Persona
import mu.KotlinLogging
import utils.toPrettyJson
import java.io.File

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalStdlibApi::class)
object JsonService: PersonasStorageService {

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}personasJson.json"
    private val fichero = File(path)
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val jsonAdapter = moshi.adapter<List<PersonaDto>>()

    override fun exportar(items: List<Persona>) {
        logger.debug { "${MORADO}JsonService$RESET -> Exportando a JSON" }
        fichero.writeText(jsonAdapter.toPrettyJson(items.toPersonaDtoList().personas))
    }

    override fun importar(): List<Persona> {
        logger.debug { "${MORADO}JsonService$RESET -> Importando desde JSON" }
        return ((jsonAdapter.fromJson(fichero.readText()) as PersonasListDto).toPersonaList() ?: emptyList())
    }
}
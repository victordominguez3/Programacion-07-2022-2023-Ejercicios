package services.storage

import MORADO
import RESET
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import models.Medicion
import mu.KotlinLogging
import utils.LocalDateAdapter
import utils.LocalTimeAdapter
import utils.toPrettyJson
import java.io.File

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalStdlibApi::class)
object JsonService: MedicionStorageService {

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompletoJson.json"
    private val moshi = Moshi.Builder()
        .add(LocalTimeAdapter())
        .add(LocalDateAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val jsonAdapter = moshi.adapter<List<Medicion>>()

    override fun exportar(items: List<Medicion>) {
        logger.debug { "${MORADO}JsonService$RESET -> Exportando a JSON" }
        val fichero = File(path)
        fichero.writeText(jsonAdapter.toPrettyJson(items))
    }

    override fun importar(): List<Medicion> {
        logger.debug { "${MORADO}JsonService$RESET -> Importando desde JSON" }
        val fichero = File(path)
        return jsonAdapter.fromJson(fichero.readText()) ?: emptyList()
    }
}
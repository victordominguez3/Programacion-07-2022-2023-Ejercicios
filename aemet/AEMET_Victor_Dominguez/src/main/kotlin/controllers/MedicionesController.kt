package controllers

import MORADO
import RESET
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.MedicionException
import exceptions.MedicionNoValidaException
import models.Medicion
import mu.KotlinLogging
import repositories.MedicionesRepository
import services.storage.MedicionStorageService
import validators.validar

private val logger = KotlinLogging.logger {}

class MedicionesController(
    private val repository: MedicionesRepository<Medicion>,
    private val storage: MedicionStorageService
) {

    init {
//        importar()
    }

    fun importar() {
        logger.debug { "${MORADO}Controller$RESET -> Importar al repositorio desde storage" }
        repository.importar(storage.importar())
    }

    fun exportar() {
        logger.debug { "${MORADO}Controller$RESET -> Exportar a storage desde el repositorio" }
        storage.exportar(repository.exportar())
    }

    fun buscarTodos(): List<Medicion> {
        logger.debug { "${MORADO}Controller$RESET -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun guardar(item: Medicion): Result<Medicion, MedicionException> {
        logger.debug { "${MORADO}Controller$RESET -> Guardar" }
        return repository.guardar(item).also { item.validar() }?. let { Ok(it) }
            ?: Err(MedicionNoValidaException("Medición no válida"))
    }

    fun tempMaxPorDia(): Map<Int, Double> {
        return repository.tempMaxPorDia()
    }

    fun tempMinPorDia(): Map<Int, Double> {
        return repository.tempMinPorDia()
    }

    fun tempMaxPorLugar(): Map<String, Double> {
        return repository.tempMaxPorLugar()
    }

    fun tempMinPorLugar(): Map<String, Double> {
        return repository.tempMinPorLugar()
    }

    fun tempMaxPorProvincia(): Map<String, String> {
        return repository.tempMaxPorProvincia()
    }

    fun tempMinPorProvincia(): Map<String, String> {
        return repository.tempMinPorProvincia()
    }

    fun tempMediaPorProvincia(): Map<String, Double> {
        return repository.tempMediaPorProvincia()
    }

    fun precipitacionMediaPorDia(): Map<Int, Double> {
        return repository.precipitacionMediaPorDia()
    }

    fun precipitacionMediaPorProvincia(): Map<String, Double> {
        return repository.precipitacionMediaPorProvincia()
    }

    fun numLugaresLlovioPorDia(): Map<Int, Int> {
        return repository.numLugaresLlovioPorDia()
    }

    fun numLugaresLlovioPorProvincia(): Map<String, Int> {
        return repository.numLugaresLlovioPorProvincia()
    }

    fun tempMediaMadrid(): Double {
        return repository.tempMediaMadrid()
    }

    fun mediaTemperaturaMaximaTotal(): Double {
        return repository.mediaTemperaturaMaximaTotal()
    }

    fun mediaTemperaturaMinimaTotal(): Double {
        return repository.mediaTemperaturaMinimaTotal()
    }

    fun lugaresTempMaxAntesDe15PorDia(): Map<Int, List<String>> {
        return repository.lugaresTempMaxAntesDe15PorDia()
    }

    fun lugaresTempMinDespuesDe1730PorDia(): Map<Int, List<String>> {
        return repository.lugaresTempMinDespuesDe1730PorDia()
    }
}
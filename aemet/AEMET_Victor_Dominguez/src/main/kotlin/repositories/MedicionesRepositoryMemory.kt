package repositories

import MORADO
import RESET
import models.Medicion
import mu.KotlinLogging
import validators.validar
import java.time.LocalTime

private val logger = KotlinLogging.logger {}

class MedicionesRepositoryMemory: MedicionesRepository<Medicion> {

    var mediciones = mutableListOf<Medicion>()

    override fun importar(list: List<Medicion>) {
        logger.debug { "${MORADO}Repository$RESET -> Importando al repositorio" }
        mediciones.clear()
        mediciones = list.toMutableList()
    }

    override fun exportar(): List<Medicion> {
        logger.debug { "${MORADO}Repository$RESET -> Exportando desde el repositorio" }
        return mediciones
    }

    override fun buscarTodos(): List<Medicion> {
        logger.debug { "${MORADO}Repository$RESET -> Buscar todos" }
        return mediciones
    }

    override fun guardar(item: Medicion): Medicion? {
        logger.debug { "${MORADO}Repository$RESET -> Guardando: $item" }
        mediciones.add(item)
        return item
    }

    override fun tempMaxPorDia(): Map<Int, Double> {
        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
            .mapValues { it.value.maxOf { it.tempMax } }
    }

    override fun tempMinPorDia(): Map<Int, Double> {
        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
            .mapValues { it.value.minOf { it.tempMin } }
    }

    override fun tempMaxPorLugar(): Map<String, Double> {
        return mediciones.groupBy { it.lugar }
            .mapValues { it.value.maxOf { it.tempMax } }
    }

    override fun tempMinPorLugar(): Map<String, Double> {
        return mediciones.groupBy { it.lugar }
            .mapValues { it.value.minOf { it.tempMin } }
    }

    override fun tempMaxPorProvincia(): Map<String, String> {
        return mediciones.groupBy { it.provincia }
            .mapValues { it.value.maxBy { it.tempMax } }
            .mapValues { it.value.let { "Día: ${it.fechaMedicion.dayOfMonth}, Lugar: ${it.lugar}, Valor: ${it.tempMax}, Hora: ${it.horaMax}" } }
    }

    override fun tempMinPorProvincia(): Map<String, String> {
        return mediciones.groupBy { it.provincia }
            .mapValues { it.value.minBy { it.tempMin } }
            .mapValues { it.value.let { "Día: ${it.fechaMedicion.dayOfMonth}, Lugar: ${it.lugar}, Valor: ${it.tempMin}, Hora: ${it.horaMin}" } }
    }

    override fun tempMediaPorProvincia(): Map<String, Double> {
        return mediciones.groupBy { it.provincia }
            .mapValues { it.value.map { (it.tempMax + it.tempMin)/2 }.average() }
    }

    override fun precipitacionMediaPorDia(): Map<Int, Double> {
        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
            .mapValues { it.value.map { it.precipitacion }.average() }
    }

    override fun precipitacionMediaPorProvincia(): Map<String, Double> {
        return mediciones.groupBy { it.provincia }
            .mapValues { it.value.map { it.precipitacion }.average() }
    }

    override fun numLugaresLlovioPorDia(): Map<Int, Int> {
        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
            .mapValues { it.value.count { it.precipitacion != 0.0 } }
    }

    override fun numLugaresLlovioPorProvincia(): Map<String, Int> {
        return mediciones.groupBy { it.provincia }
            .mapValues { it.value.count { it.precipitacion != 0.0 } }
    }

    override fun tempMediaMadrid(): Double {
        return mediciones.filter { it.provincia == "Madrid" }
            .map { (it.tempMax + it.tempMin)/2 }.average()
    }

    override fun mediaTemperaturaMaximaTotal(): Double {
        return mediciones.map { it.tempMax }.average()
    }

    override fun mediaTemperaturaMinimaTotal(): Double {
        return mediciones.map { it.tempMin }.average()
    }

    override fun lugaresTempMaxAntesDe15PorDia(): Map<Int, List<String>> {
        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
            .mapValues { it.value.filter { it.horaMax.isBefore(LocalTime.of(15, 0)) } }
            .mapValues { it.value.map { it.lugar } }
    }

    override fun lugaresTempMinDespuesDe1730PorDia(): Map<Int, List<String>> {
        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
            .mapValues { it.value.filter { it.horaMin.isAfter(LocalTime.of(17, 30)) } }
            .mapValues { it.value.map { it.lugar } }
    }
}
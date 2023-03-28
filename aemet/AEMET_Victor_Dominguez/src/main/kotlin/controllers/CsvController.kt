package controllers

import models.Medicion
import repositories.CsvRepository
import java.io.File

class CsvController(private val repository: CsvRepository<Medicion>) {

    fun leerCSVs(): List<Medicion> {
        return repository.leerCSVs()
    }

    fun escribirCSVcompleto(): File {
        return repository.escribirCSVcompleto()
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
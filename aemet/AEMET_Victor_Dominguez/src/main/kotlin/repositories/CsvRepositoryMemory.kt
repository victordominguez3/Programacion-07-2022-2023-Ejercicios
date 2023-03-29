package repositories

import models.Medicion
import toLocalTime
import java.io.File
import java.time.LocalDate
import java.time.LocalTime

class CsvRepositoryMemory: CsvRepository<Medicion> {

    val mediciones = leerCSVs()

    override fun leerCSVs(): List<Medicion> {
        val rutaCarpeta = "${System.getProperty("user.dir")}${File.separator}data"
        val carpeta = File(rutaCarpeta)
        val archivos = carpeta.listFiles()

        val listaMediciones = mutableListOf<Medicion>()

        for (i in archivos!!) {
            listaMediciones += i.readLines()
                .map { linea -> linea.split(";") }
                .map { columnas ->
                    Medicion(
                        columnas[0],
                        columnas[1],
                        columnas[2].toDouble(),
                        columnas[3].toLocalTime(),
                        columnas[4].toDouble(),
                        columnas[5].toLocalTime(),
                        columnas[6].toDouble()
                    )
                }
        }

//        val path1 = "${System.getProperty("user.dir")}${File.separator}data${File.separator}Aemet20171029.csv"
//        val path2 = "${System.getProperty("user.dir")}${File.separator}data${File.separator}Aemet20171030.csv"
//        val path3 = "${System.getProperty("user.dir")}${File.separator}data${File.separator}Aemet20171031.csv"
//        val fichero1 = File(path1)
//        val fichero2 = File(path2)
//        val fichero3 = File(path3)

//        val lista1 = fichero1.readLines()
//            .map { linea -> linea.split(";") }
//            .map { columnas ->
//                Medicion(
//                    columnas[0],
//                    columnas[1],
//                    columnas[2].toDouble(),
//                    columnas[3].toLocalTime(),
//                    columnas[4].toDouble(),
//                    columnas[5].toLocalTime(),
//                    columnas[6].toDouble()
//                )
//            }
//
//        val lista2 = fichero2.readLines()
//            .map { linea -> linea.split(";") }
//            .map { columnas ->
//                Medicion(
//                    columnas[0],
//                    columnas[1],
//                    columnas[2].toDouble(),
//                    columnas[3].toLocalTime(),
//                    columnas[4].toDouble(),
//                    columnas[5].toLocalTime(),
//                    columnas[6].toDouble()
//                )
//            }
//
//        val lista3 = fichero3.readLines()
//            .map { linea -> linea.split(";") }
//            .map { columnas ->
//                Medicion(
//                    columnas[0],
//                    columnas[1],
//                    columnas[2].toDouble(),
//                    columnas[3].toLocalTime(),
//                    columnas[4].toDouble(),
//                    columnas[5].toLocalTime(),
//                    columnas[6].toDouble()
//                )
//            }
//
//        for (i in lista1) {
//            i.fechaMedicion = LocalDate.of(2017, 10, 29)
//        }
//
//        for (i in lista2) {
//            i.fechaMedicion = LocalDate.of(2017, 10, 30)
//        }
//
//        for (i in lista3) {
//            i.fechaMedicion = LocalDate.of(2017, 10, 31)
//        }

//        return lista1 + lista2 + lista3

        return listaMediciones
    }

    override fun escribirCSVcompleto(): File {
        val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompleto.csv"
        val fichero = File(path)

        fichero.writeText("Fecha;Lugar;Provincia;Temp. Máx;Hora Máx;Temp. Min;Hora Min;Precipitación\n")
        mediciones.forEach {
            fichero.appendText("" +
//                    "${it.fechaMedicion}" +
                    ";${it.lugar};${it.provincia};${it.tempMax};${it.horaMax};${it.tempMin};${it.horaMin};${it.precipitacion}\n")
        }

        return fichero
    }

//    override fun tempMaxPorDia(): Map<Int, Double> {
//        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
//            .mapValues { it.value.maxOf { it.tempMax } }
//    }
//
//    override fun tempMinPorDia(): Map<Int, Double> {
//        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
//            .mapValues { it.value.minOf { it.tempMin } }
//    }
//
//    override fun tempMaxPorLugar(): Map<String, Double> {
//        return mediciones.groupBy { it.lugar }
//            .mapValues { it.value.maxOf { it.tempMax } }
//    }
//
//    override fun tempMinPorLugar(): Map<String, Double> {
//        return mediciones.groupBy { it.lugar }
//            .mapValues { it.value.minOf { it.tempMin } }
//    }
//
//    override fun tempMaxPorProvincia(): Map<String, String> {
//        return mediciones.groupBy { it.provincia }
//            .mapValues { it.value.maxBy { it.tempMax } }
//            .mapValues { it.value.let { "Día: ${it.fechaMedicion.dayOfMonth}, Lugar: ${it.lugar}, Valor: ${it.tempMax}, Hora: ${it.horaMax}" } }
//    }
//
//    override fun tempMinPorProvincia(): Map<String, String> {
//        return mediciones.groupBy { it.provincia }
//            .mapValues { it.value.minBy { it.tempMin } }
//            .mapValues { it.value.let { "Día: ${it.fechaMedicion.dayOfMonth}, Lugar: ${it.lugar}, Valor: ${it.tempMin}, Hora: ${it.horaMin}" } }
//    }
//
//    override fun tempMediaPorProvincia(): Map<String, Double> {
//        return mediciones.groupBy { it.provincia }
//            .mapValues { it.value.map { (it.tempMax + it.tempMin)/2 }.average() }
//    }
//
//    override fun precipitacionMediaPorDia(): Map<Int, Double> {
//        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
//            .mapValues { it.value.map { it.precipitacion }.average() }
//    }
//
//    override fun precipitacionMediaPorProvincia(): Map<String, Double> {
//        return mediciones.groupBy { it.provincia }
//            .mapValues { it.value.map { it.precipitacion }.average() }
//    }
//
//    override fun numLugaresLlovioPorDia(): Map<Int, Int> {
//        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
//            .mapValues { it.value.count { it.precipitacion != 0.0 } }
//    }
//
//    override fun numLugaresLlovioPorProvincia(): Map<String, Int> {
//        return mediciones.groupBy { it.provincia }
//            .mapValues { it.value.count { it.precipitacion != 0.0 } }
//    }
//
//    override fun tempMediaMadrid(): Double {
//        return mediciones.filter { it.provincia == "Madrid" }
//            .map { (it.tempMax + it.tempMin)/2 }.average()
//    }
//
//    override fun mediaTemperaturaMaximaTotal(): Double {
//        return mediciones.map { it.tempMax }.average()
//    }
//
//    override fun mediaTemperaturaMinimaTotal(): Double {
//        return mediciones.map { it.tempMin }.average()
//    }
//
//    override fun lugaresTempMaxAntesDe15PorDia(): Map<Int, List<String>> {
//        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
//            .mapValues { it.value.filter { it.horaMax.isBefore(LocalTime.of(15, 0)) } }
//            .mapValues { it.value.map { it.lugar } }
//    }
//
//    override fun lugaresTempMinDespuesDe1730PorDia(): Map<Int, List<String>> {
//        return mediciones.groupBy { it.fechaMedicion.dayOfMonth }
//            .mapValues { it.value.filter { it.horaMin.isAfter(LocalTime.of(17, 30)) } }
//            .mapValues { it.value.map { it.lugar } }
//    }
}
package repositories

import utils.LocalDateAdapter
import utils.LocalTimeAdapter
import dto.MedicionDto
import dto.MedicionesListDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import models.Medicion
import org.simpleframework.xml.core.Persister
import toLocalTime
import utils.toPrettyJson
import java.io.File
import java.time.LocalDate
import java.time.LocalTime

class CsvRepositoryMemory: CsvRepository<Medicion> {

    var mediciones = leerCSVs()

    override fun leerCSVs(): List<Medicion> {
        val rutaCarpeta = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources"
        val carpeta = File(rutaCarpeta)
        val archivos = carpeta.listFiles()
        val regex = Regex("Aemet[0-9]{8}\\.csv")

        val listaMediciones = mutableListOf<Medicion>()

        for (archivo in archivos!!) {
            if (archivo.name.matches(regex)) { //si el archivo no cumple la expresion regular, no entra
                val fecha = extraerFecha(archivo)
                listaMediciones += archivo.readLines()
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
                for (medicion in listaMediciones) {
                    if (medicion.fechaMedicion == LocalDate.of(1, 1, 1)) {
                        medicion.fechaMedicion = fecha
                    }
                }
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

    private fun extraerFecha(archivo: File): LocalDate {
        val nombreArchivo = archivo.name
        val anyo = "${nombreArchivo[5]}${nombreArchivo[6]}${nombreArchivo[7]}${nombreArchivo[8]}".toInt()
        val mes = "${nombreArchivo[9]}${nombreArchivo[10]}".toInt()
        val dia = "${nombreArchivo[11]}${nombreArchivo[12]}".toInt()
        return LocalDate.of(anyo, mes, dia)
    }

    override fun escribirCSVcompleto() {
        val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompletoCsv.csv"
        val fichero = File(path)

        fichero.writeText("Fecha;Lugar;Provincia;Temp. Máx;Hora Máx;Temp. Min;Hora Min;Precipitación\n")
        mediciones.forEach {
            fichero.appendText("${it.fechaMedicion}" + ";${it.lugar};${it.provincia};${it.tempMax};${it.horaMax};${it.tempMin};${it.horaMin};${it.precipitacion}\n")
        }
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

    @OptIn(ExperimentalStdlibApi::class)
    override fun escribirJson(): File {
        val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompletoJson.json"
        val fichero = File(path)

        val moshi = Moshi.Builder()
            .add(LocalTimeAdapter())
            .add(LocalDateAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter = moshi.adapter<List<Medicion>>()

        fichero.writeText(jsonAdapter.toPrettyJson(mediciones))

        return fichero
    }

    override fun escribirXml(): File {
        val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompletoXml.xml"
        val fichero = File(path)

        val serializer = Persister()

        serializer.write(mediciones.toMedicionesListDto(), fichero)

        return fichero
    }

    private fun Medicion.toMedicionDto() = MedicionDto(
        fechaMedicion = fechaMedicion.toString(),
        lugar = lugar,
        provincia = provincia,
        tempMax = tempMax,
        horaMax = horaMax.toString(),
        tempMin = tempMin,
        horaMin = horaMin.toString(),
        precipitacion = precipitacion
    )

    private fun List<Medicion>.toMedicionesListDto() = MedicionesListDto(
        mediciones = map { it.toMedicionDto() }
    )
}
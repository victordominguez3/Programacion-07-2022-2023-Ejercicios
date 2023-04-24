package services.storage

import MORADO
import RESET
import models.Medicion
import mu.KotlinLogging
import toLocalTime
import java.io.File
import java.time.LocalDate
import kotlin.math.log

private val logger = KotlinLogging.logger {}

object CsvService: MedicionStorageService {

    override fun exportar(items: List<Medicion>) {
        logger.debug { "${MORADO}CsvService$RESET -> Exportando a CSV" }
        val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}AemetCompletoCsv.csv"
        val fichero = File(path)

        fichero.writeText("Fecha;Lugar;Provincia;Temp. Máx;Hora Máx;Temp. Min;Hora Min;Precipitación\n")
        items.forEach {
            fichero.appendText("${it.fechaMedicion};${it.lugar};${it.provincia};${it.tempMax};${it.horaMax};${it.tempMin};${it.horaMin};${it.precipitacion}\n")
        }
    }

    override fun importar(): List<Medicion> {
        logger.debug { "${MORADO}CsvService$RESET -> Importando desde CSV" }
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
        logger.debug { "${MORADO}CsvService$RESET -> Asignar fecha" }
        val nombreArchivo = archivo.name
        val anyo = "${nombreArchivo[5]}${nombreArchivo[6]}${nombreArchivo[7]}${nombreArchivo[8]}".toInt()
        val mes = "${nombreArchivo[9]}${nombreArchivo[10]}".toInt()
        val dia = "${nombreArchivo[11]}${nombreArchivo[12]}".toInt()
        return LocalDate.of(anyo, mes, dia)
    }
}
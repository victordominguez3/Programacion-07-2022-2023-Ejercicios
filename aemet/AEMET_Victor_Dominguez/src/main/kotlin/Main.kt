import controllers.CsvController
import models.Medicion
import repositories.CsvRepository
import repositories.CsvRepositoryMemory
import java.io.File
import java.time.LocalDate
import java.time.LocalTime


const val AZUL = "\u001B[36m"
const val RESET = "\u001B[0m"

fun main(args: Array<String>) {

    val controller = CsvController(CsvRepositoryMemory())

    controller.escribirCSVcompleto()
    controller.escribirXml()
    controller.escribirJson()

}


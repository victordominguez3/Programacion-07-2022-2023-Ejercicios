import controllers.MedicionesController
import repositories.MedicionesRepositoryMemory
import services.storage.CsvService
import services.storage.JsonService
import services.storage.XmlService


const val AZUL = "\u001B[36m"
const val MORADO = "\u001B[35m"
const val RESET = "\u001B[0m"

fun main(args: Array<String>) {

    val csvController = MedicionesController(MedicionesRepositoryMemory(), CsvService)
    val jsonController = MedicionesController(MedicionesRepositoryMemory(), JsonService)
    val xmlController = MedicionesController(MedicionesRepositoryMemory(), XmlService)

    csvController.importar()
    val lista = csvController.buscarTodos()
    for (i in lista) {
        jsonController.guardar(i)
        xmlController.guardar(i)
    }
    csvController.exportar()
    jsonController.exportar()
    xmlController.exportar()
}


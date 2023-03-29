package models

import AZUL
import RESET
import java.time.LocalDate
import java.time.LocalTime

class Medicion(
    val lugar: String,
    val provincia: String,
    val tempMax: Double,
    val horaMax: LocalTime,
    val tempMin: Double,
    val horaMin: LocalTime,
    val precipitacion: Double
) {
    var fechaMedicion: LocalDate = LocalDate.of(1, 1, 1)

    override fun toString(): String {
        return "${AZUL}Fecha$RESET: $fechaMedicion,\t" +
                "${AZUL}Lugar$RESET: $lugar,\t" +
                "${AZUL}Provincia$RESET: $provincia,\t" +
                "${AZUL}Temperatura Máxima$RESET: $tempMax,\t" +
                "${AZUL}Hora Temp. Máx.$RESET: $horaMax,\t" +
                "${AZUL}Temperatura Mínima$RESET: $tempMin,\t" +
                "${AZUL}Hora Temp. Min.$RESET: $horaMin,\t" +
                "${AZUL}Precipitacion$RESET: $precipitacion"
    }

}
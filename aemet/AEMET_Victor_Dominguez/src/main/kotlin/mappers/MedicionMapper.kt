package mappers

import dto.MedicionDto
import dto.MedicionesListDto
import models.Medicion
import toLocalDate
import toLocalTime

fun Medicion.toMedicionDto() = MedicionDto(
    fechaMedicion = fechaMedicion.toString(),
    lugar = lugar,
    provincia = provincia,
    tempMax = tempMax,
    horaMax = horaMax.toString(),
    tempMin = tempMin,
    horaMin = horaMin.toString(),
    precipitacion = precipitacion
)

fun List<Medicion>.toMedicionesListDto() = MedicionesListDto(
    mediciones = map { it.toMedicionDto() }
)

fun MedicionDto.toMedicion() = Medicion(
        lugar = lugar,
        provincia = provincia,
        tempMax = tempMax,
        horaMax = horaMax.toLocalTime(),
        tempMin = tempMin,
        horaMin = horaMin.toLocalTime(),
        precipitacion = precipitacion
).also { it.fechaMedicion = fechaMedicion.toLocalDate() }

fun MedicionesListDto.toMedicionesList() = mediciones.map { it.toMedicion() }
package mappers

import dto.PersonaDto
import dto.PersonasListDto
import exceptions.PersonaException
import exceptions.PersonaNoValidaException
import models.Alumno
import models.Persona
import models.Profesor

fun Persona.toPersonaDto(): PersonaDto {
    if (this is Profesor) {
        return PersonaDto(
            tipo = "Profesor",
            nombre = nombre,
            edad = "",
            modulo = modulo
        )
    }
    if (this is Alumno) {
        return PersonaDto(
            tipo = "Alumno",
            nombre = nombre,
            edad = edad.toString(),
            modulo = ""
        )
    }
    return throw PersonaNoValidaException("La persona debe ser Profesor o Alumno")
}

fun List<Persona>.toPersonaDtoList() = PersonasListDto(
    personas = map { it.toPersonaDto() }
)

fun PersonaDto.toPersona(): Persona {
    if (tipo == "Profesor") {
        return Profesor(
            nombre = nombre,
            modulo = modulo
        )
    }
    if (tipo == "Alumno") {
        return Alumno(
            nombre = nombre,
            edad = edad.toInt()
        )
    }
    return throw PersonaNoValidaException("La persona debe ser Profesor o Alumno")
}

fun PersonasListDto.toPersonaList() = personas.map { it.toPersona() }
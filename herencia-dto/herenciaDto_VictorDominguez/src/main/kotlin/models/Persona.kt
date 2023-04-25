package models

import AZUL
import RESET

sealed class Persona(val nombre: String)

class Profesor(
    nombre: String,
    val modulo: String
): Persona(nombre) {
    override fun toString(): String {
        return "${AZUL}Profesor$RESET -> Nombre: $nombre, Módulo: $modulo"
    }
}

class Alumno(
    nombre: String,
    val edad: Int
): Persona(nombre) {
    override fun toString(): String {
        return "${AZUL}Alumno$RESET -> Nombre: $nombre, Edad: $edad"
    }
}
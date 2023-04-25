package repositories

import MORADO
import RESET
import factories.PersonasFactory.crearClaseEntera
import models.Alumno
import models.Persona
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

class PersonasRepositoryMemory: PersonasRepository<Persona> {

    var personas = mutableListOf<Persona>()

    override fun importar(list: List<Persona>) {
        logger.debug { "${MORADO}Repository$RESET -> Importando al repositorio" }
        personas.clear()
        personas = list.toMutableList()
    }

    override fun exportar(): List<Persona> {
        logger.debug { "${MORADO}Repository$RESET -> Exportando desde el repositorio" }
        return personas
    }

    override fun crearClase(): List<Persona> {
        logger.debug { "${MORADO}Repository$RESET -> Creando clase nueva" }
        personas = crearClaseEntera().toMutableList()
        return personas
    }

    override fun buscarTodos(): List<Persona> {
        logger.debug { "${MORADO}Repository$RESET -> Buscar todos" }
        return personas
    }

    override fun guardar(item: Persona): Persona {
        logger.debug { "${MORADO}Repository$RESET -> Guardar: $item" }
        personas.add(item)
        return item
    }

    override fun alumnoMasMayor(): Persona? {
        return personas.filter { it is Alumno }
            .maxByOrNull { (it as Alumno).edad }
    }

    override fun alumnoMasJoven(): Persona? {
        return personas.filter { it is Alumno }
            .minByOrNull { (it as Alumno).edad }
    }

    override fun mediaEdadAlumnos(): Double {
        return personas.filter { it is Alumno }
            .map { (it as Alumno).edad }
            .average()
    }

    override fun mediaLongitudNombres(): Double {
        return personas.map { it.nombre.length }
            .average()
    }

    override fun listarPorTipo(): Map<String?, List<Persona>> {
        return personas.groupBy { it::class.simpleName }
    }
}
package controller

import MORADO
import RESET
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.AlumnoNoEncontradoException
import exceptions.PersonaNoValidaException
import models.Alumno
import models.Persona
import models.Profesor
import mu.KotlinLogging
import repositories.PersonasRepository
import services.storage.PersonasStorageService
import validators.validar

private val logger = KotlinLogging.logger {}

class PersonaController(
    private val repository: PersonasRepository<Persona>,
    private val storage: PersonasStorageService
) {
    fun importar() {
        logger.debug { "${MORADO}Controller$RESET -> Importar al repositorio desde storage" }
        repository.importar(storage.importar())
    }

    fun exportar() {
        logger.debug { "${MORADO}Controller$RESET -> Exportando a storage desde el repositorio" }
        storage.exportar(repository.exportar())
    }

    fun crearClase(): List<Persona> {
        logger.debug { "${MORADO}Controller$RESET -> Crear clase nueva" }
        return repository.crearClase()
    }

    fun buscarTodos(): List<Persona> {
        logger.debug { "${MORADO}Controller$RESET -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun guardar(item: Persona): Result<Persona, PersonaNoValidaException> {
        logger.debug { "${MORADO}Controller$RESET -> Guardar: $item" }
        return repository.guardar(item).also { if (item is Profesor) {item.validar()} else if (item is Alumno) {item.validar()} }
            ?.let { Ok(it) }
            ?: Err(PersonaNoValidaException("La persona a guardar no es válida"))
    }

    fun alumnoMasMayor(): Result<Persona, AlumnoNoEncontradoException> {
        return repository.alumnoMasMayor()?.let { Ok(it) }
            ?: Err(AlumnoNoEncontradoException("No hay alumnos en el repositorio"))
    }

    fun alumnoMasJoven(): Result<Persona, AlumnoNoEncontradoException> {
        return repository.alumnoMasJoven()?.let { Ok(it) }
            ?: Err(AlumnoNoEncontradoException("No hay alumnos en el repositorio"))
    }

    fun mediaEdadAlumnos(): Double {
        return repository.mediaEdadAlumnos()
    }

    fun mediaLongitudNombres(): Double {
        return repository.mediaLongitudNombres()
    }

    fun listarPorTipo(): Map<String?, List<Persona>> {
        return repository.listarPorTipo()
    }
}
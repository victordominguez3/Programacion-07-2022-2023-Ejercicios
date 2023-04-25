package repositories

interface PersonasRepository<T> {
    fun importar(list: List<T>)
    fun exportar(): List<T>
    fun crearClase(): List<T>
    fun buscarTodos(): List<T>
    fun guardar(item: T): T?
    fun alumnoMasMayor(): T?
    fun alumnoMasJoven(): T?
    fun mediaEdadAlumnos(): Double
    fun mediaLongitudNombres(): Double
    fun listarPorTipo(): Map<String?, List<T>>
}
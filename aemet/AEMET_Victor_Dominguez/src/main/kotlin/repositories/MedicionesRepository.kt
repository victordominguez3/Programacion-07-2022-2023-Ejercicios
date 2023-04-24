package repositories

interface MedicionesRepository<T> {
    fun importar(list: List<T>)
    fun exportar(): List<T>
    fun buscarTodos(): List<T>
    fun guardar(item: T): T?
    fun tempMaxPorDia(): Map<Int, Double>
    fun tempMinPorDia(): Map<Int, Double>
    fun tempMaxPorLugar(): Map<String, Double>
    fun tempMinPorLugar(): Map<String, Double>
    fun tempMaxPorProvincia(): Map<String, String>
    fun tempMinPorProvincia(): Map<String, String>
    fun tempMediaPorProvincia(): Map<String, Double>
    fun precipitacionMediaPorDia(): Map<Int, Double>
    fun precipitacionMediaPorProvincia(): Map<String, Double>
    fun numLugaresLlovioPorDia(): Map<Int, Int>
    fun numLugaresLlovioPorProvincia(): Map<String, Int>
    fun tempMediaMadrid(): Double
    fun mediaTemperaturaMaximaTotal(): Double
    fun mediaTemperaturaMinimaTotal(): Double
    fun lugaresTempMaxAntesDe15PorDia(): Map<Int, List<String>>
    fun lugaresTempMinDespuesDe1730PorDia(): Map<Int, List<String>>
}
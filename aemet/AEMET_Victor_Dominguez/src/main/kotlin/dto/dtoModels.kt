package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "medicion")
data class MedicionDto(

    @field:Attribute(name = "fecha")
    @param:Attribute(name = "fecha")
    val fechaMedicion: String = "",

    @field:Element(name = "lugar")
    @param:Element(name = "lugar")
    val lugar: String = "",

    @field:Element(name = "provincia")
    @param:Element(name = "provincia")
    val provincia: String = "",

    @field:Element(name = "temperaturaMaxima")
    @param:Element(name = "temperaturaMaxima")
    val tempMax: Double = 0.0,

    @field:Element(name = "horaTempMax")
    @param:Element(name = "horaTempMax")
    val horaMax: String = "",

    @field:Element(name = "temperaturaMinima")
    @param:Element(name = "temperaturaMinima")
    val tempMin: Double = 0.0,

    @field:Element(name = "horaTempMin")
    @param:Element(name = "horaTempMin")
    val horaMin: String = "",

    @field:Element(name = "precipitacion")
    @param:Element(name = "precipitacion")
    val precipitacion: Double = 0.0
)

@Root(name = "listaMediciones")
data class MedicionesListDto(
    @field:ElementList(name = "mediciones")
    @param:ElementList(name = "mediciones")
    val mediciones: List<MedicionDto>
)
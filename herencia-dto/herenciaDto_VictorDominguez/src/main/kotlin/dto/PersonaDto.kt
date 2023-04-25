package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name= "persona")
class PersonaDto(
    @field:Attribute(name = "tipo")
    @param:Attribute(name = "tipo")
    val tipo: String,

    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @field:Element(name = "edad")
    @param:Element(name = "edad")
    val edad: String,

    @field:Element(name = "modulo")
    @param:Element(name = "modulo")
    val modulo: String
)

@Root(name = "personas")
class PersonasListDto(
    @field:ElementList(name = "personas", inline = true)
    @param:ElementList(name = "personas", inline = true)
    val personas: List<PersonaDto>
)
package utils

import com.squareup.moshi.*
import java.time.LocalDate
import java.time.LocalTime

class LocalTimeAdapter : JsonAdapter<LocalTime>() {
    @FromJson
    override fun fromJson(reader: JsonReader): LocalTime? = LocalTime.parse(reader.readJsonValue().toString())

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalTime?) {
        writer.jsonValue(value.toString())
    }
}

class LocalDateAdapter : JsonAdapter<LocalDate>() {
    @FromJson
    override fun fromJson(reader: JsonReader): LocalDate? = LocalDate.parse(reader.readJsonValue().toString())

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        writer.jsonValue(value.toString())
    }
}

fun <T> JsonAdapter<T>.toPrettyJson(value: T): String = this.indent("  ").toJson(value)
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String.toLocalTime(): LocalTime {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    var hora = "0"
    if (this.length == 4) {
        hora += this
        return LocalTime.parse(hora, formatter)
    }
    return LocalTime.parse(this, formatter)
}
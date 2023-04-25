package utils

import com.squareup.moshi.JsonAdapter

fun <T> JsonAdapter<T>.toPrettyJson(value: T): String = this.indent("  ").toJson(value)
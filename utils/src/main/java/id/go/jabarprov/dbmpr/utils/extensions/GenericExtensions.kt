package id.go.jabarprov.dbmpr.utils.extensions

fun <T> T?.getValueOrElse(value: T): T {
    return if (this == null) {
        value
    } else if (this is String && this.isBlank()) {
        value
    } else if (this is Collection<*> && this.isEmpty()) {
        value
    } else {
        value
    }
}

fun <T, R> T?.getValueOrElse(callback: () -> R): R {
    return if (this == null) {
        callback()
    } else if (this is String && this.isBlank()) {
        callback()
    } else if (this is Collection<*> && this.isEmpty()) {
        callback()
    } else {
        callback()
    }
}
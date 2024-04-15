package dev.maxsiomin.common.domain

sealed interface LocationError : Error {

    data object GpsDisabled : LocationError
    data object MissingPermission : LocationError
    data object Unknown : LocationError

}
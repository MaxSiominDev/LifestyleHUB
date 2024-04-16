package dev.maxsiomin.common.domain.resource

sealed interface DatabaseError : Error {

    data object NotFound : DatabaseError

}
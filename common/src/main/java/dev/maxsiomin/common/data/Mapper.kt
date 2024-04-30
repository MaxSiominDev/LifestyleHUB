package dev.maxsiomin.common.data

// Sealed so that no classes from other modules would be able to inherit from this interface
sealed interface BaseMapper<Data, Domain> {

    fun toDomain(data: Data): Domain

    fun toData(domain: Domain): Data

}

abstract class ToDataMapper<Data, Domain> : BaseMapper<Data, Domain> {

    final override fun toDomain(data: Data): Domain {
        throw RuntimeException("Not implemented")
    }

}

abstract class ToDomainMapper<Data, Domain> : BaseMapper<Data, Domain> {

    final override fun toData(domain: Domain): Data {
        throw RuntimeException("Not implemented")
    }

}

abstract class BidirectionalMapper<Data, Domain> : BaseMapper<Data, Domain>

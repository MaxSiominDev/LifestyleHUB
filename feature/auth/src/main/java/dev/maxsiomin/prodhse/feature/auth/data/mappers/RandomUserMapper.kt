package dev.maxsiomin.prodhse.feature.auth.data.mappers

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.Result
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import javax.inject.Inject

internal class RandomUserMapper @Inject constructor() : ToDomainMapper<Result, RandomUserData> {

    override fun toDomain(data: Result): RandomUserData {
        val nameObject = data.name
        val fullName = "${nameObject.first} ${nameObject.last}"
        return RandomUserData(
            fullName = fullName,
            avatarUrl = data.picture.large,
        )
    }

}
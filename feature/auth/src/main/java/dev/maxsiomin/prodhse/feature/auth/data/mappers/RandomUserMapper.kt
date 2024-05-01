package dev.maxsiomin.prodhse.feature.auth.data.mappers

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.Result
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUser
import javax.inject.Inject

class RandomUserMapper @Inject constructor() : ToDomainMapper<Result, RandomUser> {

    override fun toDomain(data: Result): RandomUser {
        val nameObject = data.name
        val fullName = "${nameObject.first} ${nameObject.last}"
        return RandomUser(
            fullName = fullName,
            avatarUrl = data.picture.large,
        )
    }

}
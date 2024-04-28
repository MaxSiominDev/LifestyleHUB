package dev.maxsiomin.prodhse.feature.auth.data.mappers

import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.Result
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUser

class RandomUserDtoToModelMapper : (Result) -> RandomUser {

    override fun invoke(result: Result): RandomUser {
        val nameObject = result.name
        val fullName = "${nameObject.first} ${nameObject.last}"
        return RandomUser(
            fullName = fullName,
            avatarUrl = result.picture.large,
        )
    }

}
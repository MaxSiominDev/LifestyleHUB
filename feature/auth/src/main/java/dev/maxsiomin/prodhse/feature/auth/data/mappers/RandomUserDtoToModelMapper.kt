package dev.maxsiomin.prodhse.feature.auth.data.mappers

import dev.maxsiomin.prodhse.feature.auth.data.dto.Result
import dev.maxsiomin.prodhse.feature.auth.domain.RandomUserModel

class RandomUserDtoToModelMapper : (Result) -> RandomUserModel {

    override fun invoke(result: Result): RandomUserModel {
        val nameObject = result.name
        val fullName = "${nameObject.first} ${nameObject.last}"
        return RandomUserModel(
            fullName = fullName,
            avatarUrl = result.picture.large,
        )
    }

}
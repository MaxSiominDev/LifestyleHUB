package dev.maxsiomin.prodhse.feature.auth.data.mappers

import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivityModel

class RandomActivityDtoToUiModelMapper : (BoredApiResponse) -> RandomActivityModel? {

    override fun invoke(response: BoredApiResponse): RandomActivityModel? {
        val name = response.activity ?: return null
        val participants = response.participants ?: return null
        return RandomActivityModel(
            name = name,
            participants = participants,
        )
    }

}
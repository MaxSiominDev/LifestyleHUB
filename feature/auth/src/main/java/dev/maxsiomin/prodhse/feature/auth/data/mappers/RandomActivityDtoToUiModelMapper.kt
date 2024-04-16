package dev.maxsiomin.prodhse.feature.auth.data.mappers

import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivity

class RandomActivityDtoToUiModelMapper : (BoredApiResponse) -> RandomActivity? {

    override fun invoke(response: BoredApiResponse): RandomActivity? {
        val name = response.activity ?: return null
        val participants = response.participants ?: return null
        return RandomActivity(
            name = name,
            participants = participants,
        )
    }

}
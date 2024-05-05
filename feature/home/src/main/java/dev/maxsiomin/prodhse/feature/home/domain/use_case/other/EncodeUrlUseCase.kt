package dev.maxsiomin.prodhse.feature.home.domain.use_case.other

import java.net.URLEncoder
import javax.inject.Inject

internal class EncodeUrlUseCase @Inject constructor() {

    operator fun invoke(url: String): String {
        return URLEncoder.encode(url, "UTF-8")
    }

}
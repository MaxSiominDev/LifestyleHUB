package dev.maxsiomin.prodhse.feature.home.domain.use_case.other

import java.net.URLDecoder
import javax.inject.Inject

internal class DecodeUrlUseCase @Inject constructor() {

    operator fun invoke(encodedUrl: String): String {
        return URLDecoder.decode(encodedUrl, "UTF-8")
    }

}
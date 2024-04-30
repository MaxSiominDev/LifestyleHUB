package dev.maxsiomin.common.domain.use_case

interface UseCase {

    operator fun invoke()

}

interface SuspendUseCase {

    suspend operator fun invoke()

}

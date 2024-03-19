package dev.maxsiomin.prodhse.core

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

interface PullRefresher {

    var isLoading: State<Boolean>

    fun refresh()

}

/** For previews only */
class FakePullRefresherViewModel : ViewModel(), PullRefresher {
    override var isLoading: State<Boolean> = mutableStateOf(true)

    override fun refresh() {

    }
}

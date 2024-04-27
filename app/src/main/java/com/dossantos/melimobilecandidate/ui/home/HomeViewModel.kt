package com.dossantos.melimobilecandidate.ui.home

import androidx.lifecycle.MutableLiveData
import com.dossantos.melimobilecandidate.ui.base.BaseViewModel
import com.dossantos.melimobilecandidate.utils.Integers.oneSecond
import com.dossantos.melimobilecandidate.utils.doIfNull
import kotlinx.coroutines.delay

class HomeViewModel(
    override val mutableStateUi: MutableLiveData<HomeStateUi> =
        MutableLiveData<HomeStateUi>(HomeStateUi())
) : BaseViewModel<HomeStateUi>() {
    private val text = "Hello From ViewModel"
    private val error = "Erro inesperado"

    override fun init() {
        getState().currentState.doIfNull {
            updateState(getState().showLoading())
            runOnMain {
                delay(oneSecond)
                updateState(getState().onError(error))
                delay(oneSecond)
                updateState(getState().showLoading())
                delay(oneSecond)
                updateState(getState().onSuccess(text))
            }
        }
    }

}

package com.sgs.devcamp2.observableobjectprac

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val _liveData = MutableLiveData("Hello World")
    val liveData: LiveData<String> = _liveData

    // 한번 onCreate 된 상태에선 값이 동일하면 갱신하지 않음
    // 단, 화면이 다시 create 되함 다시 클릭하지 않아도 값을 갱신
    private val _stateFlow = MutableStateFlow("Hello World")
    val stateFlow: StateFlow<String> = _stateFlow

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow: MutableSharedFlow<String> = _sharedFlow

    fun triggerLiveData() {
        _liveData.value = "LiveData"
    }

    fun triggerStateFlow() {
        _stateFlow.value = "StateFlow"
    }

    // 트리거 되면 5초간 카운팅
    // 이와 같은 스레딩 동작에 flow를 씀
    fun triggerFlow(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Item $it")
                delay(1000L)
            }
        }
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }
}

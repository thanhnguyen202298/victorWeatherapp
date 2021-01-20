package com.victorthanh.utilslib.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.victorthanh.utilslib.domain.model.message.AppMessage
import com.victorthanh.utilslib.domain.model.message.AppMessageLiveData
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var nagFlag: MutableLiveData<Int> = MutableLiveData()

    var inProgressStatus: MutableLiveData<Boolean> = MutableLiveData()

    var appMessage: AppMessageLiveData = AppMessageLiveData(AppMessage())


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    open fun showCloudError(throwable: Throwable) {
        appMessage.setCloudErrorMessage(throwable)
        inProgressStatus.value = false
    }

    open fun onBackPress() {
        //TODO not implemented
    }

    fun checkResponseCode(code: Int, throwable: Throwable) {
        val unauthorized = code == 401 || code == 403
        appMessage.setCloudErrorMessage(throwable)
    }
}
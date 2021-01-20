package com.victorthanh.utilslib.domain.usecase

import us.fuvi.utils_sdk.base.BaseUseCase
import io.reactivex.disposables.CompositeDisposable

class AuthUseCase(private val compositeDisposable: CompositeDisposable) : BaseUseCase() {


}

typealias AuthClosures = (Boolean) -> Unit
typealias NetExceptptor= (Throwable) -> Unit
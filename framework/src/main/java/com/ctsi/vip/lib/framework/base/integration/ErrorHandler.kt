package com.ctsi.vip.lib.framework.base.integration

interface ErrorHandler {
    fun handleError(throwable: Throwable?): Boolean {
        return false
    }
}
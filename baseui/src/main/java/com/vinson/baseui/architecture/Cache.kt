package com.vinson.baseui.architecture

class Cache<T> {
    var isExpired = true
        private set
    private var data: T? = null

    fun get(ignoreExpiration: Boolean = false): T?
            = if (ignoreExpiration || !isExpired) data else null

    fun set(value: T?) {
        isExpired = false
        data = value
    }

    fun setExpired() {
        isExpired = true
    }
}
package com.bytegeek.quickchat.data.network


class NetworkResponse<T> {


    var status: Status
    var message: String?
    var statusCode: String? = null
    var response: T

    constructor(status: Status, message: String?, response: T) {
        this.status = status
        this.message = message
        this.response = response
    }

    constructor(status: Status, message: String?, statusMessage: String?, response: T) {
        this.status = status
        this.message = message
        this.response = response
        this.statusCode = statusMessage
    }

    companion object {
        fun <T> idle(): NetworkResponse<T?> {
            return NetworkResponse(Status.IDLE, null, null)
        }


        fun <T> loading(): NetworkResponse<T?> {
            return NetworkResponse(Status.LOADING, null, null)
        }

        fun <T> success(data: T, message: String?): NetworkResponse<T> {
            return NetworkResponse(Status.SUCCESS, message, data)
        }

        fun <T> success(data: T): NetworkResponse<T> {
            return NetworkResponse(Status.SUCCESS, null, data)
        }

        fun <T> success(message: String?): NetworkResponse<T?> {
            return NetworkResponse(Status.SUCCESS, message, null)
        }

        fun <T> success(message: String?, statusMessage: String): NetworkResponse<T?> {
            return NetworkResponse(Status.SUCCESS, message, statusMessage, null)
        }

        fun <T> error(error: String?): NetworkResponse<T?> {
            return NetworkResponse(Status.ERROR, error, null)
        }

        fun <T> error(error: String?, statusCode: String?): NetworkResponse<T?> {
            return NetworkResponse(Status.ERROR, error, statusCode, null)
        }
    }
}
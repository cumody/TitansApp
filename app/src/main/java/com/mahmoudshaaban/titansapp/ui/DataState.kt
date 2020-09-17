package com.mahmoudshaaban.titansapp.ui

data class DataState<T>(
    var error: Event<StateError>? = null,
    var loading: Loading = Loading(false),
    var data: Data<T>? = null
) {

    companion object {

        fun <T> error(
            response: Response
        ): DataState<T> {
            return DataState(
                error = Event(
                    StateError(
                        response
                    )
                ),
                loading = Loading(false),
                data = null
            )
        }

        fun <T> loading(
            isLoading: Boolean,
            cachedData: T? = null
        ): DataState<T> {
            return DataState(
                error = null,
                loading = Loading(isLoading),
                // we won't to create an event if the data is null
                data = Event.dataEvent(
                    cachedData
                )?.let {
                    Data(
                        it, null
                    )
                }
            )
        }

        fun <T> data(
            data: T? = null,
            response: Response? = null
        ): DataState<T> {
            return DataState(
                error = null,
                loading = Loading(false),
                data = Event.dataEvent(data)?.let {
                    Event.responseEvent(response)?.let { it1 ->
                        Data(
                            it,
                            it1
                        )
                    }
                }
            )
        }
    }
}
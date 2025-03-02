package org.scesi.domain.models.shared

sealed class ReportResult<out S, out E> {
    data class Success<out S>(val data: S) : ReportResult<S, Nothing>()
    data class Error<out E>(val error: E) : ReportResult<Nothing, E>()
}
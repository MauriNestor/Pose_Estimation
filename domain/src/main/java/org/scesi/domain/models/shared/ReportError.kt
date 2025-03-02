package org.scesi.domain.models.shared

sealed class ReportError {
    class Server(val code: Int, val message: String = "") : ReportError()
    object Connectivity : ReportError()
    class Unknown(val message: String = "") : ReportError()
}
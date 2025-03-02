package org.scesi.data.dataSources

interface ReportLocalDataSource {
    suspend fun getReports(): ReportResult<List<Report>, ReportError>
}
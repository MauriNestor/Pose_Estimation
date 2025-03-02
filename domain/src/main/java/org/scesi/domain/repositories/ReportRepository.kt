package org.scesi.domain.repositories

import org.scesi.domain.models.reports.Report
import org.scesi.domain.models.shared.ReportError
import org.scesi.domain.models.shared.ReportResult

interface ReportRepository {
    suspend fun getReport(): ReportResult<List<Report>, ReportError>
}
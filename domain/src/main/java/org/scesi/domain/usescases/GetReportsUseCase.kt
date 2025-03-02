package org.scesi.domain.usescases

import org.scesi.domain.repositories.ReportRepository

class GetReportsUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke() =
        reportRepository.getAllReport()
}
package com.company.taskportal.service;

import com.company.taskportal.dto.DashboardSummaryResponse;

public interface DashboardService {

    /**
     * Returns the dashboard summary statistics.
     *
     * @return dashboard summary
     */
    DashboardSummaryResponse getDashboardSummary();

}
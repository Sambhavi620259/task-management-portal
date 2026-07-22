package com.company.taskportal.service;

import java.io.ByteArrayInputStream;

public interface ExportService {

    ByteArrayInputStream exportTaskReportToExcel();

    ByteArrayInputStream exportTaskReportToPdf();

}
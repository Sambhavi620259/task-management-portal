package com.company.taskportal.service;

import com.company.taskportal.entity.Task;
import com.company.taskportal.repository.TaskRepository;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExportServiceImpl implements ExportService {

    private final TaskRepository taskRepository;

    @Override
    public ByteArrayInputStream exportTaskReportToExcel() {

        List<Task> tasks = taskRepository.findByDeletedFalse();

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {

            Sheet sheet = workbook.createSheet("Tasks");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("Task Code");
            header.createCell(1).setCellValue("Task Name");
            header.createCell(2).setCellValue("Project");
            header.createCell(3).setCellValue("Department");
            header.createCell(4).setCellValue("Employee");
            header.createCell(5).setCellValue("Priority");
            header.createCell(6).setCellValue("Status");
            header.createCell(7).setCellValue("Start Date");
            header.createCell(8).setCellValue("Due Date");
            header.createCell(9).setCellValue("Completion %");

            int rowIndex = 1;
            for (Task task : tasks) {

                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(task.getTaskCode());

                row.createCell(1).setCellValue(task.getTaskName());

                row.createCell(2).setCellValue(
                        task.getProject() != null
                                ? task.getProject().getProjectName()
                                : ""
                );

                row.createCell(3).setCellValue(
                        task.getDepartment() != null
                                ? task.getDepartment().getDepartmentName()
                                : ""
                );

                row.createCell(4).setCellValue(
                        task.getAssignedTo() != null
                                ? task.getAssignedTo().getEmployeeCode()
                                : ""
                );

                row.createCell(5).setCellValue(
                        task.getPriority() != null
                                ? task.getPriority().name()
                                : ""
                );

                row.createCell(6).setCellValue(
                        task.getStatus() != null
                                ? task.getStatus().name()
                                : ""
                );

                row.createCell(7).setCellValue(
                        task.getStartDate() != null
                                ? task.getStartDate().toString()
                                : ""
                );

                row.createCell(8).setCellValue(
                        task.getDueDate() != null
                                ? task.getDueDate().toString()
                                : ""
                );

                row.createCell(9).setCellValue(
                        task.getCompletionPercentage() != null
                                ? task.getCompletionPercentage()
                                : 0
                );
            }

            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Failed to export task report to Excel.",
                    ex
            );
        }
    }

    @Override
    public ByteArrayInputStream exportTaskReportToPdf() {

        List<Task> tasks = taskRepository.findByDeletedFalse();

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);

            Paragraph title = new Paragraph(
                    "Task Report",
                    titleFont
            );

            title.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);

            table.setWidths(
                    new float[]{
                            2f,
                            4f,
                            3f,
                            3f,
                            2f,
                            2f,
                            2f,
                            2f
                    }
            );

            addHeader(table, "Task Code");
            addHeader(table, "Task Name");
            addHeader(table, "Project");
            addHeader(table, "Department");
            addHeader(table, "Employee");
            addHeader(table, "Priority");
            addHeader(table, "Status");
            addHeader(table, "Completion");

            for (Task task : tasks) {

                table.addCell(
                        task.getTaskCode() != null
                                ? task.getTaskCode()
                                : ""
                );

                table.addCell(
                        task.getTaskName() != null
                                ? task.getTaskName()
                                : ""
                );

                table.addCell(
                        task.getProject() != null
                                ? task.getProject().getProjectName()
                                : ""
                );

                table.addCell(
                        task.getDepartment() != null
                                ? task.getDepartment().getDepartmentName()
                                : ""
                );

                table.addCell(
                        task.getAssignedTo() != null
                                ? task.getAssignedTo().getEmployeeCode()
                                : ""
                );

                table.addCell(
                        task.getPriority() != null
                                ? task.getPriority().name()
                                : ""
                );

                table.addCell(
                        task.getStatus() != null
                                ? task.getStatus().name()
                                : ""
                );

                table.addCell(
                        String.valueOf(
                                task.getCompletionPercentage() != null
                                        ? task.getCompletionPercentage()
                                        : 0
                        ) + "%"
                );
            }

            document.add(table);

            document.close();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (DocumentException | IOException ex) {

            throw new RuntimeException(
                    "Failed to export task report to PDF.",
                    ex
            );
        }
    }

    private void addHeader(PdfPTable table, String title) {

        Font font = new Font(Font.HELVETICA, 11, Font.BOLD);

        PdfPCell header = new PdfPCell();

        header.setPhrase(new Phrase(title, font));

        header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        table.addCell(header);
    }

}
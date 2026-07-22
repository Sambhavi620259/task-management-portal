package com.company.taskportal.common;

import com.company.taskportal.entity.Task;

public final class EmailTemplateUtil {

    private EmailTemplateUtil() {
        // Utility class
    }

    public static String assignmentTemplate(Task task) {

        String employeeName = task.getAssignedTo().getFirstName()
                + " "
                + task.getAssignedTo().getLastName();

        String projectName = task.getProject() != null
                ? task.getProject().getProjectName()
                : "N/A";

        String departmentName = task.getDepartment() != null
                ? task.getDepartment().getDepartmentName()
                : "N/A";

        String dueDate = task.getDueDate() != null
                ? task.getDueDate().toString()
                : "N/A";

        String priority = task.getPriority() != null
                ? task.getPriority().name()
                : "N/A";

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                </head>

                <body style="margin:0;padding:0;background:#f4f6f9;font-family:Arial,Helvetica,sans-serif;">

                <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f4f6f9;padding:30px;">
                    <tr>
                        <td align="center">

                            <table width="700" cellpadding="0" cellspacing="0"
                                   style="background:#ffffff;border-radius:8px;overflow:hidden;
                                          box-shadow:0 2px 10px rgba(0,0,0,0.15);">

                                <tr>
                                    <td style="background:#0d6efd;color:#ffffff;padding:25px;text-align:center;">
                                        <h2 style="margin:0;">Task Management Portal</h2>
                                    </td>
                                </tr>

                                <tr>
                                    <td style="padding:30px;">

                                        <h3>Hello %s,</h3>

                                        <p>
                                            A new task has been assigned to you.
                                        </p>

                                        <table width="100%%"
                                               cellpadding="10"
                                               cellspacing="0"
                                               border="1"
                                               style="border-collapse:collapse;">

                                            <tr>
                                                <th align="left">Task Code</th>
                                                <td>%s</td>
                                            </tr>

                                            <tr>
                                                <th align="left">Task Name</th>
                                                <td>%s</td>
                                            </tr>

                                            <tr>
                                                <th align="left">Project</th>
                                                <td>%s</td>
                                            </tr>

                                            <tr>
                                                <th align="left">Department</th>
                                                <td>%s</td>
                                            </tr>

                                            <tr>
                                                <th align="left">Due Date</th>
                                                <td>%s</td>
                                            </tr>

                                            <tr>
                                                <th align="left">Priority</th>
                                                <td>
                                                    <strong style="color:#dc3545;">%s</strong>
                                                </td>
                                            </tr>

                                        </table>

                                        <br>

                                        <p>
                                            Please login to the Task Management Portal
                                            to view complete task details.
                                        </p>

                                        <p>
                                            Thank you.
                                        </p>

                                    </td>
                                </tr>

                                <tr>
                                    <td style="background:#eeeeee;padding:18px;text-align:center;font-size:12px;color:#666666;">
                                        © 2026 Task Management Portal. All Rights Reserved.
                                    </td>
                                </tr>

                            </table>

                        </td>
                    </tr>
                </table>

                </body>
                </html>
                """
                .formatted(
                        employeeName,
                        task.getTaskCode(),
                        task.getTaskName(),
                        projectName,
                        departmentName,
                        dueDate,
                        priority
                );
    }

}
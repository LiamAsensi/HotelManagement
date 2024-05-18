package edu.carlosliam.hotelmanagementfx.utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class EmployeeReportPdfCreator {
    private static final String REPORTS_FOLDER = "reports/";
    private final String destination;
    private final ReportType reportType;
    private final List<Employee> employees;
    private final HashMap<ReportType, String> reportTypeMap = new HashMap<>() {{
        put(ReportType.ALL_EMPLOYEES, "All Employees");
        put(ReportType.FREE_EMPLOYEES, "Available Employees");
    }};

    public EmployeeReportPdfCreator(ReportType reportType, List<Employee> employees) {
        this.reportType = reportType;
        this.employees = employees;
        this.destination = REPORTS_FOLDER + reportType.name().toLowerCase()
                + "/report-" + LocalDate.now() + ".pdf";
    }

    public void createPdf() {
        try {
            PdfWriter writer = new PdfWriter(destination);
            PdfDocument pdf = new PdfDocument(writer);

            Document doc = new Document(pdf);

            doc.add(new Paragraph(reportTypeMap.get(reportType) + " Report").setBold().setFontSize(20));
            doc.add(new Paragraph("")).add(new Paragraph(""));
            doc.add(createEmployeeTable());

            doc.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Cell createCell(String content) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setTextAlignment(TextAlignment.LEFT);
        cell.setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
        cell.setPadding(10);
        return cell;
    }

    private Table createEmployeeTable() {
        Table table = new Table(new float[]{1, 2, 2, 2});
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(createCell("ID").setBold().setBackgroundColor(new DeviceRgb(252, 69, 3)).setFontColor(new DeviceRgb(255, 255, 255)));
        table.addHeaderCell(createCell("Full Name").setBold().setBackgroundColor(new DeviceRgb(252, 69, 3)).setFontColor(new DeviceRgb(255, 255, 255)));
        table.addHeaderCell(createCell("Email").setBold().setBackgroundColor(new DeviceRgb(252, 69, 3)).setFontColor(new DeviceRgb(255, 255, 255)));
        table.addHeaderCell(createCell("DNI").setBold().setBackgroundColor(new DeviceRgb(252, 69, 3)).setFontColor(new DeviceRgb(255, 255, 255)));

        employees.forEach(employee -> {
            if (employees.indexOf(employee) % 2 == 0) {
                table.addCell(createCell(employee.getId()));
                table.addCell(createCell(employee.getName() + " " + employee.getSurnames()));
                table.addCell(createCell(employee.getEmail()));
                table.addCell(createCell(employee.getDni()));
            } else {
                table.addCell(createCell(employee.getId()).setBackgroundColor(new DeviceRgb(217, 217, 217)));
                table.addCell(createCell(employee.getName() + " " + employee.getSurnames()).setBackgroundColor(new DeviceRgb(217, 217, 217)));
                table.addCell(createCell(employee.getEmail()).setBackgroundColor(new DeviceRgb(217, 217, 217)));
                table.addCell(createCell(employee.getDni()).setBackgroundColor(new DeviceRgb(217, 217, 217)));
            }
        });

        return table;
    }

    public enum ReportType {
        ALL_EMPLOYEES,
        FREE_EMPLOYEES
    }
}

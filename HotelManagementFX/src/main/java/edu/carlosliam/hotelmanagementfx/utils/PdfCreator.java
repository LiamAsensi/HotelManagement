package edu.carlosliam.hotelmanagementfx.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class PdfCreator {
    private static final String PAYCHECK_FOLDER = "paychecks/";
    private static final float PRICE_PER_HOUR = 8.28F;
    private final String destination;
    private final Employee employee;
    private final String startDate;
    private final String endDate;
    private final List<Assignment> assignments;

    public PdfCreator(Employee employee, String startDate,
                      String endDate, List<Assignment> assignments) {
        this.destination = PAYCHECK_FOLDER + "paycheck-employee" + employee.getId() +
        "_" + startDate + "_" + endDate + ".pdf";
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignments = assignments;
    }

    public void createPdf() {
        try {
            PdfWriter writer = new PdfWriter(destination);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.add(generateHeader());

            doc.add(new Paragraph(""));
            doc.add(new Paragraph("Paycheck from " + startDate + " to " + endDate));
            doc.add(new Paragraph(""));
            doc.add(new Paragraph(""));

            if (!assignments.isEmpty()) {
                doc.add(generateTaskTable());

            } else {
                doc.add(new Paragraph("No tasks done."));
            }

            doc.add(new Paragraph(""));
            doc.add(new Paragraph(""));
            doc.add(new Paragraph("Total salary: " + String.format("%.2f", getSalary(assignments)) + "€"));

            doc.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Cell createCell(String content, TextAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell createTaskCell(Assignment assignment) {
        Cell cell = new Cell();

        Table taskTable = new Table(new float[]{1, 1});
        taskTable.setBorder(Border.NO_BORDER);

        taskTable.addCell(createCell("Assignment " + assignment.getCodTask(), TextAlignment.LEFT));
        taskTable.addCell(createCell("Type: " + assignment.getType(), TextAlignment.RIGHT));
        taskTable.addCell(createCell(assignment.getDescription(), TextAlignment.RIGHT));

        cell.add(taskTable);

        cell.setBorderTop(Border.NO_BORDER);
        cell.setBorderLeft(Border.NO_BORDER);
        cell.setBorderRight(Border.NO_BORDER);

        return cell;
    }

    private Table generateHeader() {
        Table headerDataTable = new Table(new float[]{1, 1});
        headerDataTable.setWidth(UnitValue.createPercentValue(100));
        headerDataTable.setBorder(Border.NO_BORDER);
        headerDataTable.setPadding(5);

        Table businessDataTable = new Table(new float[]{1, 2});
        businessDataTable.setBorder(Border.NO_BORDER);

        businessDataTable.addCell(createCell("Business:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("Hotel Management", TextAlignment.RIGHT));

        businessDataTable.addCell(createCell("CIF:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("A12345678", TextAlignment.RIGHT));

        businessDataTable.addCell(createCell("Address:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("1234 Main St, Springfield, IL 62701", TextAlignment.RIGHT));

        businessDataTable.addCell(createCell("Email:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("iessanvicente@gmail.com", TextAlignment.RIGHT));

        Table employeeDataTable = new Table(new float[]{1, 2});
        employeeDataTable.setBorder(Border.NO_BORDER);

        employeeDataTable.addCell(createCell("Employee:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getName() + " " + employee.getSurnames(), TextAlignment.RIGHT));

        employeeDataTable.addCell(createCell("DNI:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getDni(), TextAlignment.RIGHT));

        employeeDataTable.addCell(createCell("Profession:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getProfession(), TextAlignment.RIGHT));

        employeeDataTable.addCell(createCell("Email:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getEmail(), TextAlignment.RIGHT));

        headerDataTable.addCell(new Cell().add(businessDataTable).setBorder(Border.NO_BORDER));
        headerDataTable.addCell(new Cell().add(employeeDataTable).setBorder(Border.NO_BORDER));

        return headerDataTable;
    }

    private Table generateTaskTable() {
        Table table = new Table(new float[]{3, 1});
        table.setWidth(UnitValue.createPercentValue(100));
        table.setBorder(Border.NO_BORDER);

        assignments.forEach(a -> {
            table.addCell(createTaskCell(a));
            table.addCell(createCell(a.getEstimatedTime() + " hours", TextAlignment.RIGHT));
        });

        return table;
    }

    private double getSalary(List<Assignment> assignments) {
        return assignments
                .stream()
                .mapToDouble(a -> a.getEstimatedTime() * PRICE_PER_HOUR)
                .sum();
    }
}

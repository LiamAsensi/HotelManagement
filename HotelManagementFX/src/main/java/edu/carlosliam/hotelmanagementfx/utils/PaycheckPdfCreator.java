package edu.carlosliam.hotelmanagementfx.utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
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
import java.util.List;

public class PaycheckPdfCreator {
    private static final String PAYCHECK_FOLDER = "paychecks/";
    private static final float PRICE_PER_HOUR = 8.28F;
    private final String destination;
    private final Employee employee;
    private final String startDate;
    private final String endDate;
    private final List<Assignment> assignments;

    public String getDestination() {
        return destination;
    }

    public PaycheckPdfCreator(Employee employee, String startDate,
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
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, this::generateFooter);

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
        taskTable.setWidth(UnitValue.createPercentValue(100));

        taskTable.addCell(createCell("Assignment " + assignment.getCodTask(), TextAlignment.LEFT));
        taskTable.addCell(createCell("Type: " + assignment.getType(), TextAlignment.RIGHT));
        taskTable.addCell(createCell(assignment.getDescription(), TextAlignment.LEFT));
        taskTable.addCell(createCell("", TextAlignment.LEFT));

        cell.add(taskTable);

        cell.setBorderTop(Border.NO_BORDER);
        cell.setBorderLeft(Border.NO_BORDER);
        cell.setBorderRight(Border.NO_BORDER);

        cell.setBackgroundColor(new DeviceRgb(255, 218, 212));

        return cell;
    }

    private Table generateHeader() {
        Table headerDataTable = new Table(new float[]{2, 1, 2});
        headerDataTable.setWidth(UnitValue.createPercentValue(100));
        headerDataTable.setBorder(Border.NO_BORDER);
        headerDataTable.setPadding(5);

        Table businessDataTable = new Table(new float[]{1, 2});
        businessDataTable.setBorder(Border.NO_BORDER);
        businessDataTable.setWidth(UnitValue.createPercentValue(100));

        businessDataTable.addCell(createCell("Business:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("Hotel Management", TextAlignment.RIGHT));

        businessDataTable.addCell(createCell("CIF:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("A12345678", TextAlignment.RIGHT));

        businessDataTable.addCell(createCell("Address:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("1234 Main St, Springfield", TextAlignment.RIGHT));

        businessDataTable.addCell(createCell("Email:", TextAlignment.LEFT));
        businessDataTable.addCell(createCell("iessanvicente@gmail.com", TextAlignment.RIGHT));

        Table employeeDataTable = new Table(new float[]{1, 2});
        employeeDataTable.setBorder(Border.NO_BORDER);
        employeeDataTable.setWidth(UnitValue.createPercentValue(100));

        employeeDataTable.addCell(createCell("Employee:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getName() + " " + employee.getSurnames(), TextAlignment.RIGHT));

        employeeDataTable.addCell(createCell("DNI:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getDni(), TextAlignment.RIGHT));

        employeeDataTable.addCell(createCell("Profession:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getProfession(), TextAlignment.RIGHT));

        employeeDataTable.addCell(createCell("Email:", TextAlignment.LEFT));
        employeeDataTable.addCell(createCell(employee.getEmail(), TextAlignment.RIGHT));

        headerDataTable.addCell(new Cell().add(businessDataTable).setBorder(Border.NO_BORDER));
        headerDataTable.addCell(new Cell().setWidth(64).setBorder(Border.NO_BORDER));
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

    private Table generateFooterTable() {
        Table footerTable = new Table(new float[]{1, 1});
        footerTable.setWidth(UnitValue.createPercentValue(100));
        footerTable.setBorderBottom(Border.NO_BORDER);
        footerTable.setBorderLeft(Border.NO_BORDER);
        footerTable.setBorderRight(Border.NO_BORDER);

        footerTable.addCell(createCell("Total hours:", TextAlignment.LEFT));
        footerTable.addCell(createCell(String.format("%.2f", getTotalHours()) + " hours", TextAlignment.RIGHT));

        footerTable.addCell(createCell("Price per hour:", TextAlignment.LEFT));
        footerTable.addCell(createCell(PRICE_PER_HOUR + " €/h", TextAlignment.RIGHT));

        footerTable.addCell(createCell("Total salary:", TextAlignment.LEFT));
        footerTable.addCell(createCell(String.format("%.2f", getSalary()) + "€", TextAlignment.RIGHT));

        return footerTable;
    }

    private void generateFooter(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();

        new Canvas(page, page.getPageSize())
                .add(generateFooterTable().setFixedPosition(32, 15, pageSize.getWidth() - 64));
    }

    private double getSalary() {
        return assignments
                .stream()
                .mapToDouble(a -> a.getEstimatedTime() * PRICE_PER_HOUR)
                .sum();
    }

    private double getTotalHours() {
        return assignments
                .stream()
                .mapToDouble(Assignment::getEstimatedTime)
                .sum();
    }
}
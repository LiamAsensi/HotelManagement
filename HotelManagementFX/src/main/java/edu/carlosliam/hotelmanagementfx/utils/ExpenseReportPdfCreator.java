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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ExpenseReportPdfCreator {
    private static final String REPORTS_FOLDER = "reports/expenses/";
    private static final float PRICE_PER_HOUR = 8.28F;
    private final String destination;
    private final String startDate;
    private final String endDate;
    private final List<Assignment> assignments;

    public ExpenseReportPdfCreator(List<Assignment> assignments, String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignments = assignments;
        this.destination = REPORTS_FOLDER + "report-" + LocalDate.now() + ".pdf";
    }

    public void createPdf() {
        try {
            PdfWriter writer = new PdfWriter(destination);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, this::generateFooter);

            Document doc = new Document(pdf);

            doc.add(new Paragraph("Expense Report (From " + startDate + " to " + endDate + ")").setBold().setFontSize(20));
            doc.add(new Paragraph("")).add(new Paragraph(""));
            doc.add(createTaskTable());

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
        cell.setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
        cell.setPadding(10);
        return cell;
    }

    private Table createTaskTable() {
        Table table = new Table(new float[]{1, 2, 2, 2});
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(createCell("ID", TextAlignment.LEFT)
                .setBold()
                .setBackgroundColor(new DeviceRgb(252, 69, 3))
                .setFontColor(new DeviceRgb(255, 255, 255)));

        table.addHeaderCell(createCell("Employee ID", TextAlignment.LEFT)
                .setBold()
                .setBackgroundColor(new DeviceRgb(252, 69, 3))
                .setFontColor(new DeviceRgb(255, 255, 255)));

        table.addHeaderCell(createCell("Type", TextAlignment.LEFT)
                .setBold()
                .setBackgroundColor(new DeviceRgb(252, 69, 3))
                .setFontColor(new DeviceRgb(255, 255, 255)));

        table.addHeaderCell(createCell("Time", TextAlignment.LEFT)
                .setBold()
                .setBackgroundColor(new DeviceRgb(252, 69, 3))
                .setFontColor(new DeviceRgb(255, 255, 255)));

        assignments.forEach(assignment -> {
            if (assignments.indexOf(assignment) % 2 == 0) {
                table.addCell(createCell(assignment.getCodTask(), TextAlignment.LEFT));
                table.addCell(createCell(assignment.getDescription(), TextAlignment.LEFT));
                table.addCell(createCell(assignment.getType(), TextAlignment.LEFT));
                table.addCell(createCell(assignment.getEstimatedTime() + " hours", TextAlignment.LEFT));
            } else {
                table.addCell(createCell(assignment.getCodTask(), TextAlignment.LEFT)
                        .setBackgroundColor(new DeviceRgb(217, 217, 217)));
                table.addCell(createCell(assignment.getDescription(), TextAlignment.LEFT)
                        .setBackgroundColor(new DeviceRgb(217, 217, 217)));
                table.addCell(createCell(assignment.getType(), TextAlignment.LEFT)
                        .setBackgroundColor(new DeviceRgb(217, 217, 217)));
                table.addCell(createCell(assignment.getEstimatedTime() + " hours", TextAlignment.LEFT)
                        .setBackgroundColor(new DeviceRgb(217, 217, 217)));
            }
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

        footerTable.addCell(createCell("Total expense:", TextAlignment.LEFT));
        footerTable.addCell(createCell(String.format("%.2f", getExpense()) + "€", TextAlignment.RIGHT));

        return footerTable;
    }

    private void generateFooter(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();

        new Canvas(page, page.getPageSize())
                .add(generateFooterTable().setFixedPosition(32, 15, pageSize.getWidth() - 64));
    }

    private double getExpense() {
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

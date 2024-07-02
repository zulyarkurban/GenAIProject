package org.example.app;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public void writeTestCasesToExcel(String testCases, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Cases");

        // Create a bold font style
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a regular font style
        Font regularFont = workbook.createFont();
        regularFont.setFontHeightInPoints((short) 11);
        regularFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle regularCellStyle = workbook.createCellStyle();
        regularCellStyle.setFont(regularFont);

        String[] lines = testCases.split("\n");
        int rowCount = 0;

        for (String line : lines) {
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellValue(line);

            // Apply header style for lines starting with "### Test Case"
            if (line.startsWith("### Test Case")) {
                cell.setCellStyle(headerCellStyle);
            } else {
                cell.setCellStyle(regularCellStyle);
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

        workbook.close();
    }
}
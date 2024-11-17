package com.hexaware.app;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public void writeTestCasesToExcel(String testCases, String filePath) throws IOException {
        if (testCases == null || testCases.isEmpty()) {
            throw new IllegalArgumentException("Test cases input cannot be null or empty");
        }

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Test Cases");

            // Create distinct styles
            CellStyle featureStyle = createCellStyle(workbook, true, (short) 14, IndexedColors.DARK_BLUE.getIndex());
            CellStyle scenarioStyle = createCellStyle(workbook, true, (short) 12, IndexedColors.BLACK.getIndex());
            CellStyle stepStyle = createCellStyle(workbook, false, (short) 11, IndexedColors.GREY_80_PERCENT.getIndex());

            String[] lines = testCases.split("\n");
            int rowCount = 0;

            for (String line : lines) {
                Row row = sheet.createRow(rowCount++);
                Cell cell = row.createCell(0);
                cell.setCellValue(line.trim());

                // Apply styles based on line content
                if (line.startsWith("Feature:")) {
                    cell.setCellStyle(featureStyle);
                } else if (line.startsWith("Scenario:")) {
                    cell.setCellStyle(scenarioStyle);
                } else {
                    cell.setCellStyle(stepStyle);
                }
            }

            // Auto-size the column for better readability
            sheet.autoSizeColumn(0);

            // Write the data to the specified file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        }
    }

    private CellStyle createCellStyle(Workbook workbook, boolean isBold, short fontSize, short color) {
        Font font = workbook.createFont();
        font.setBold(isBold);
        font.setFontHeightInPoints(fontSize);
        font.setColor(color);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}

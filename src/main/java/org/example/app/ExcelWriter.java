package org.example.app;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public void writeTestCasesToExcel(String testCases, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Cases");

        String[] lines = testCases.split("\n");
        int rowCount = 0;

        for (String line : lines) {
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellValue(line);
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

        workbook.close();
    }
}

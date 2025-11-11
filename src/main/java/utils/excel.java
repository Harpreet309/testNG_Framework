package utils;

import models.FormData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class excel {

    public Map<String, String> readAllData() throws IOException {
        Map<String, String> dataMap = new HashMap<>();

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/testdata.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        XSSFSheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();

        // Find the "TestData" column
        int colIndex = -1;
        for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            if (firstRow.getCell(i).getStringCellValue().equalsIgnoreCase("TestData")) {
                colIndex = i;
                break;
            }
        }

        if (colIndex == -1) throw new RuntimeException("❌ 'TestData' column not found!");

        // Read each row and store key-value pairs
        while (rows.hasNext()) {
            Row row = rows.next();
            String key = row.getCell(colIndex).getStringCellValue();
            Cell valueCell = row.getCell(colIndex + 1); // next column for value
            String value = (valueCell.getCellType() == CellType.STRING)
                    ? valueCell.getStringCellValue()
                    : NumberToTextConverter.toText(valueCell.getNumericCellValue());
            dataMap.put(key, value);
        }

        workbook.close();
        fis.close();
        return dataMap;
    }

    public FormData getFormData() throws IOException {
        Map<String, String> data = readAllData();

        return new FormData(
                data.get("First Name"),
                data.get("Last Name"),
                data.get("Email"),
                data.get("Mobile Number"),
                data.get("Subjects"),
                data.get("Current Address")
        );
    }
}
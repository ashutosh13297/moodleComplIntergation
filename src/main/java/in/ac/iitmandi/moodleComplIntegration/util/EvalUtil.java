package in.ac.iitmandi.moodleComplIntegration.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class EvalUtil {

    public static void writeDataToFile(Map<String, Integer> asgResult, String dirPath) throws IOException, IOException {
        String[] columns = {"RollNo", "Marks Obtained"};
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("AssignmentResult " + LocalDate.now());

        XSSFFont headerFont = workbook.createFont();
        headerFont.getCTFont().addNewB();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for(String rollNo : asgResult.keySet()) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(rollNo);
            row.createCell(1).setCellValue(asgResult.get(rollNo));
        }


        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fileOut = new FileOutputStream(dirPath+ "/AssignmentResult.xlsx");
        workbook.write(fileOut);
        fileOut.close();
    }

    public static void deleteDirectory(File file) {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}

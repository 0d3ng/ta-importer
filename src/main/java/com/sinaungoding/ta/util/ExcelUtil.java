package com.sinaungoding.ta.util;

import com.sinaungoding.ta.entity.TugasAkhir;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtil {
    public static List<TugasAkhir> read(File file) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);

        Workbook sheets = WorkbookFactory.create(inputStream);
        List<TugasAkhir> tugasAkhirs = new ArrayList<>();
        sheets.forEach(sheet -> {
            sheet.forEach(row -> {
                TugasAkhir tugasAkhir = new TugasAkhir();
                String sheetName = sheet.getSheetName();
                tugasAkhir.setJenjang(sheetName.split(" ")[0].equals("LA") ? "D3" : "D4");
                tugasAkhir.setTahun(Integer.parseInt(sheetName.split(" ")[1]));
                row.forEach(cell -> {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            break;
                        case 1:
                            String judul = cell.getStringCellValue();
                            tugasAkhir.setJudul(judul);
                            break;
                    }
                });
                tugasAkhirs.add(tugasAkhir);
            });
        });
        return tugasAkhirs;
    }
}

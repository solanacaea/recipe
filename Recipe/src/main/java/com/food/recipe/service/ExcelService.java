package com.food.recipe.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.food.recipe.bean.DailyBean;

@Service
public class ExcelService {

	private Workbook getWorkBook() {
		try {
			Resource resource = new ClassPathResource("template.xlsx");
			//File file = FileUtils.getFile("classpath:template.xlsx");
			return WorkbookFactory.create(resource.getInputStream());
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Workbook merge(List<DailyBean> list) throws IOException {
		
		Workbook wb = getWorkBook();
		mergeSheet(list, wb.getSheetAt(0));
		return wb;
	}

	private void mergeSheet(List<DailyBean> list, Sheet sheet) {
		
		AtomicInteger rowNum = new AtomicInteger(12);
		list.forEach(a -> {
			Row row = sheet.getRow(rowNum.getAndIncrement());
			if (row == null)
				return;
			
			Iterator<Cell> iter = row.cellIterator();
			Cell cell = iter.next();
			switch (cell.getStringCellValue()) {
				case "‘Á≤Õ":
					fillCell(iter.next(), a.getBreakfast().getMainFood());
					fillCell(iter.next(), a.getBreakfast().getSoap());
					fillCell(iter.next(), a.getBreakfast().getCai());
					break;
				case "ŒÁ≤Õ":
					fillCell(iter.next(), a.getLunch().getMainFood());
					fillCell(iter.next(), a.getLunch().getSoap());
					fillCell(iter.next(), a.getLunch().getDish1());
					fillCell(iter.next(), a.getLunch().getDish2());
					break;
				case "ÕÌ≤Õ":
					DailyBean.Dinner dinner = a.getDinner();
					fillCell(iter.next(), a.getDinner().getMainFood());
					fillCell(iter.next(), a.getDinner().getSoap());
					fillCell(iter.next(), a.getDinner().getDish1());
					fillCell(iter.next(), a.getDinner().getDish2());
					break;
				case "¡„ ≥&≤Ë“˚":
					fillCell(iter.next(), a.getSnack().getSnack1());
					fillCell(iter.next(), a.getSnack().getSnack2());
					fillCell(iter.next(), a.getSnack().getSnack3());
					break;
			}
		});
	}

	private void fillCell(Cell cell, String value) {
		if (StringUtils.isBlank(cell.getStringCellValue()))
			cell.setCellValue(value);
		else {
			
		}
	}
	
}

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
import com.food.recipe.bean.RequestBean;

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
	
	public Workbook merge(List<DailyBean> list, RequestBean b) throws IOException {
		
		Workbook wb = getWorkBook();
		Sheet sheet = wb.getSheetAt(0);
		mergeSheet(b, sheet);
		mergeSheet(list, sheet);
		return wb;
	}

	private void mergeSheet(RequestBean b, Sheet sheet) {
		
		Row clientRow = sheet.getRow(1);
		clientRow.getCell(1).setCellValue(b.getUserName());
		Row memoRow = sheet.getRow(3);
		memoRow.getCell(1).setCellValue(b.getMemo());
		Row sugRow = sheet.getRow(5);
		sugRow.getCell(1).setCellValue(b.getSuggestion());
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
				case "早餐":
					fillCell(iter.next(), a.getBreakfast().getMainFood());
					fillCell(iter.next(), a.getBreakfast().getSoap());
					fillCell(iter.next(), a.getBreakfast().getCai());
					break;
				case "午餐":
					fillCell(iter.next(), a.getLunch().getMainFood());
					fillCell(iter.next(), a.getLunch().getSoap());
					fillCell(iter.next(), a.getLunch().getDish1());
					fillCell(iter.next(), a.getLunch().getDish2());
					break;
				case "晚餐":
					fillCell(iter.next(), a.getDinner().getMainFood());
					fillCell(iter.next(), a.getDinner().getSoap());
					fillCell(iter.next(), a.getDinner().getDish1());
					fillCell(iter.next(), a.getDinner().getDish2());
					break;
				case "零食&茶饮":
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

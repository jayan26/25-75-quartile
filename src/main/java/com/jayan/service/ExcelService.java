package com.jayan.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jayan.model.ExcelData;

@Service
public class ExcelService {

	public static Map<String, Double> companyIncome = new TreeMap<String, Double>();
	public static List<Double> salesdata = new ArrayList<>();
	
	//Reading data from the Excel file
	public Iterator<Row> getIteratorasperRow(MultipartFile multipartFile) throws IOException{
		InputStream inputStream = multipartFile.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		return rowIterator;
	}
	
	//getting all the data from the excel file and returning as a Map
	public Map<String, ExcelData> getValueFromExcel(MultipartFile multipartFile) throws IOException {
		Map<String, ExcelData> allData = new TreeMap<String, ExcelData>();
		Iterator<Row> rowIterator = this.getIteratorasperRow(multipartFile);
		Row row = null;
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if (row.getRowNum() == 0) {
				for(int i=0; i<5; i++)
				companyIncome.put(row.getCell(i).toString(), 0.0);
				continue;
			}
			ExcelData excelData = new ExcelData();
			String key = row.getCell(0).toString();
			excelData.setComp1(Double.parseDouble(row.getCell(1).toString()));
			excelData.setComp2(Double.parseDouble(row.getCell(2).toString()));
			excelData.setComp3(Double.parseDouble(row.getCell(3).toString()));
			excelData.setComp4(Double.parseDouble(row.getCell(4).toString()));
			excelData.setComp5(Double.parseDouble(row.getCell(5).toString()));
			allData.put(key, excelData);
			}	
		return allData;
	}
	
	//getting the company code
	public int getCompanyCode(String companyName) {
		switch(companyName) {
		case "comp1": return 1;
		case "comp2": return 2;
		case "comp3": return 3;
		case "comp4": return 4;
		case "comp5": return 5;
		default: return 0;
		}
	}
	
	//getting the values assigned for that particular company
	public Map<String, Double> getDataPerCompany(MultipartFile multipartFile, String companyName) throws IOException {
		Iterator<Row> rowIterator = this.getIteratorasperRow(multipartFile);		
		Map<String, Double> companyData = new TreeMap<>();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() == 0)
				continue;
			companyData.put(row.getCell(0).toString(), Double.parseDouble(row.getCell(getCompanyCode(companyName)).toString()));
			}
		//sorting the values
		Map<String, Double> newMap = companyData.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(e1,e2) -> e1, LinkedHashMap::new));
		return newMap;
	}
	
	public double quartileResult(MultipartFile multipartFile, String companyName) throws IOException {
		Map<String, Double> companyData = this.getDataPerCompany(multipartFile, companyName);
		List<Double> values = new ArrayList<>(companyData.values());
		
		//find Median
		Double median = values.get((int) Math.ceil(values.size()/2));
		
		//adding lowest elements to an array
		
		
		return median;
	}

}

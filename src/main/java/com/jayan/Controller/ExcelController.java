package com.jayan.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jayan.model.ExcelData;
import com.jayan.service.ExcelService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ExcelController {

	@Autowired
	ExcelService excelService;
	
	@PostMapping(value="/file/date")
	public Map<String, ExcelData> getAllData(@RequestParam("file") MultipartFile multipartFile) throws IOException{
		System.out.println("Inside Multipart file");
		return excelService.getValueFromExcel(multipartFile);
	}
	
	@PostMapping(value="/file/company/{companyname}")
	public Map<String, Double> getDataPerCompany(@RequestParam("file") MultipartFile multipartFile ,@PathVariable("companyname") String companyname) throws IOException{
		System.out.println(excelService.quartileResult(multipartFile, companyname));
		return excelService.getDataPerCompany(multipartFile, companyname);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping(value="/test")
	public void getValues() throws IOException {
		
		List<String> list = new ArrayList<String>();
		
		File csvFile = new File("C:\\Users\\neera\\Desktop\\TestData - Copy.csv");
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String line = "";
		StringTokenizer st = null;
		while((line=br.readLine()) != null) {
			st=new StringTokenizer(line, ",");
			while(st.hasMoreElements()) {
				String sd = st.nextToken()+" ";
				if(sd!=null) {
					list.add(sd);
					System.err.println(sd);
				}
			}
			
		}
	}
	
}

package excelToJira;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class publish {

	public static String testcaseNumber;
	public static String createdBy;
	public static String scenarioDescription;
	public static  XSSFSheet spreadsheet;
	public static String tempDescription;
	public static void main(String[] args) {
		 try {
			
			  FileInputStream fis = new FileInputStream(new File("C:\\Users\\nmadipel.ORADEV\\Desktop\\PORTAL Smoke Test - 20.2.0.0.xlsx"));
			  XSSFWorkbook workbook = new XSSFWorkbook(fis);
			  spreadsheet = workbook.getSheet("Portal Admin Smoke Tests");
			  
			  int columns=spreadsheet.getRow(0).getLastCellNum();
			  int rows=spreadsheet.getLastRowNum();
			  
			  for(int i=0;i<rows;i++)
			  {
				  Cell cell=spreadsheet.getRow(i).getCell(0);
				  switch (cell.getCellType()) {

				  case STRING:
					  testcaseNumber= cell.getStringCellValue();
						  Cell createdByCell=spreadsheet.getRow(i).getCell(1);
						  switch(createdByCell.getCellType()) {
						  
							  case STRING:
								  createdBy=spreadsheet.getRow(i).getCell(1).getStringCellValue();
								  
								  if(testcaseNumber==null || testcaseNumber.isEmpty() || testcaseNumber.length()==0) {
									  
									  continue;
									 
								  }else {
									  if(createdBy.equalsIgnoreCase("Kevin")) {
										  scenarioDescription=spreadsheet.getRow(i).getCell(11).getStringCellValue();
										  System.out.println("++++++++++++++++++"+testcaseNumber +":" +scenarioDescription+"+++++++++++++++++");
										  int j;
										  for(j=i+1;j<rows;j++) {
											  tempDescription=spreadsheet.getRow(j).getCell(11).getStringCellValue();
												if(tempDescription==null || tempDescription.length()==0) {
													 String step=spreadsheet.getRow(j).getCell(14).getStringCellValue();
													 String expectedResult=spreadsheet.getRow(j).getCell(15).getStringCellValue();
													if(step.length()>1) {
														System.out.println(step +" || "+expectedResult);	
													}
													 
												}else {
													break;
												}
											 
										  }
										  i=j-1;
										  
										  
										    
									  }
									   
								  }
								  
								  
								  break;
							  case NUMERIC:
								  int val= (int) spreadsheet.getRow(i).getCell(1).getNumericCellValue();
								  createdBy=Integer.toString(val);
								  if(testcaseNumber==null || testcaseNumber.isEmpty() || testcaseNumber.length()==0) {
									  
									  continue;
									 
								  }else {
									  if(createdBy.equalsIgnoreCase("Kevin")) {
										  scenarioDescription=spreadsheet.getRow(i).getCell(11).getStringCellValue();
										  System.out.println("++++++++++++++++++"+scenarioDescription+"+++++++++++++++++");
										 int j;
										  for(j=i+1;j<rows;j++) {
											  tempDescription=spreadsheet.getRow(j).getCell(11).getStringCellValue();
												if(tempDescription==null || tempDescription.length()==0) {
													 String step=spreadsheet.getRow(j).getCell(14).getStringCellValue();
													 String expectedResult=spreadsheet.getRow(j).getCell(15).getStringCellValue();
													 if(step.length()>1) {
															System.out.println(step +" || "+expectedResult);	
														}
												}else {
													break;
												}
											 
										  }
										  i=j-1;
										  
										  
										    
									  }
									   
								  }
								  break;
							  case FORMULA:
								  int val3= (int) spreadsheet.getRow(i).getCell(1).getNumericCellValue();
								  createdBy=Integer.toString(val3);
								  break;
							  default: 
								   createdBy=spreadsheet.getRow(i).getCell(1).getStringCellValue();
									 break;
						  }
					  break;
				  
				  case NUMERIC:
					  int val= (int) cell.getNumericCellValue();
					  testcaseNumber=Integer.toString(val);
						  Cell createdByCell1=spreadsheet.getRow(i).getCell(1);
						  switch(createdByCell1.getCellType()) {
						  
							  case STRING:
								  createdBy=spreadsheet.getRow(i).getCell(1).getStringCellValue();
								  if(testcaseNumber==null || testcaseNumber.isEmpty() || testcaseNumber.length()==0) {
									  
									  continue;
									 
								  }else {
									  if(createdBy.equalsIgnoreCase("Kevin")) {
										  scenarioDescription=spreadsheet.getRow(i).getCell(11).getStringCellValue();
										  System.out.println("++++++++++++++++++"+scenarioDescription+"+++++++++++++++++");
										 int j;
										  for(j=i+1;j<rows;j++) {
											  tempDescription=spreadsheet.getRow(j).getCell(11).getStringCellValue();
												if(tempDescription==null || tempDescription.length()==0) {
													 String step=spreadsheet.getRow(j).getCell(14).getStringCellValue();
													 String expectedResult=spreadsheet.getRow(j).getCell(15).getStringCellValue();
													 if(step.length()>1) {
															System.out.println(step +" || "+expectedResult);	
														}
												}else {
													break;
												}
											 
										  }
										  i=j-1;
										  
										  
										    
									  }
									   
								  }
								  
								  break;
							  case NUMERIC:
								  int val1= (int) spreadsheet.getRow(i).getCell(1).getNumericCellValue();
								  createdBy=Integer.toString(val1);
								  if(testcaseNumber==null || testcaseNumber.isEmpty() || testcaseNumber.length()==0) {
									  
									  continue;
									 
								  }else {
									  if(createdBy.equalsIgnoreCase("Kevin")) {
										  scenarioDescription=spreadsheet.getRow(i).getCell(11).getStringCellValue();
										  System.out.println("++++++++++++++++++"+scenarioDescription+"+++++++++++++++++");
										 int j;
										  for(j=i+1;j<rows;j++) {
											  tempDescription=spreadsheet.getRow(j).getCell(11).getStringCellValue();
												if(tempDescription==null || tempDescription.length()==0) {
													 String step=spreadsheet.getRow(j).getCell(14).getStringCellValue();
													 String expectedResult=spreadsheet.getRow(j).getCell(15).getStringCellValue();
													 if(step.length()>1) {
															System.out.println(step +" || "+expectedResult);	
														}
													 
												}else {
													break;
												}
											 
										  }
										  i=j-1;
										  
										  
										    
									  }
									   
								  }
								  
								  break;
							  case BLANK:
								  createdBy="";
								  break;
							  default: 
								  createdBy=spreadsheet.getRow(i).getCell(1).getStringCellValue();
								  break;
						  }
					
					break;
				
				  
				  default:
					   testcaseNumber= cell.getStringCellValue();
					   createdBy=spreadsheet.getRow(i).getCell(1).getStringCellValue();
		           	}

				  
				  
				  
			  }
			  
			  
			  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	public static void createJiraTicket(String description) {
		final String uri = "http://localhost:8080/springrestexample/employees";
		/*
		 * RestTemplate restTemplate = new RestTemplate();
		 * 
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		 * 
		 * ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET,
		 * entity, String.class);
		 * 
		 * System.out.println(result);
		 */
	}
}

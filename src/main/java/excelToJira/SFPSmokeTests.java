package excelToJira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SFPSmokeTests {

	public static String testcaseNumber;
	public static String createdBy;
	public static String scenarioDescription;
	public static XSSFSheet spreadsheet;
	public static String tempDescription;

	public static void main(String[] args) {
		try {

			FileInputStream fis = new FileInputStream(
					new File("C:\\Users\\nmadipel.ORADEV\\Desktop\\SFP & Portal - End to End Testing v20.2.0.0.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			spreadsheet = workbook.getSheet("Smoke Tests");

			int columns = spreadsheet.getRow(0).getLastCellNum();
			int rows = spreadsheet.getLastRowNum();

			for (int i = 1; i < rows; i++) {

				scenarioDescription = spreadsheet.getRow(i).getCell(9).getStringCellValue();
				testcaseNumber = spreadsheet.getRow(i).getCell(0).getStringCellValue();
				
				boolean module=true;	
				
				int j;
				boolean logDesc=true;
				for (j = i + 1; j < rows; j++) {
					
					if(module) {
						if(scenarioDescription.length()==0){
							module=false;
						}
					}
					
					tempDescription = spreadsheet.getRow(j).getCell(9).getStringCellValue();
					if (tempDescription == null || tempDescription.length() == 0) {
						if(logDesc) {
							System.out.println(
									"++++++++++++++++++ "+testcaseNumber +":"+scenarioDescription+" +++++++++++++++++");
							logDesc=false;
						}
					
						try {
							String step = spreadsheet.getRow(j).getCell(12).getStringCellValue();
							String expectedResult = spreadsheet.getRow(j).getCell(13).getStringCellValue();
							String testData = spreadsheet.getRow(j).getCell(14).getStringCellValue();
							if (step.length() > 1) {
								System.out.println(step + " :: " + expectedResult +" ::  "+testData);
								
							}
						} catch (Exception e) {
							
							System.out.println(e);
						}

					} else {
						break;
					}

				}
				i = j - 1;

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void MyGETRequest() throws IOException {
	    URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
	    int responseCode = conection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        // print result
	        System.out.println("JSON String Result " + response.toString());
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
	}
	public static void POSTRequest() throws IOException {
	    final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
	        "    \"id\": 101,\r\n" +
	        "    \"title\": \"Test Title\",\r\n" +
	        "    \"body\": \"Test Body\"" + "\n}";
	    System.out.println(POST_PARAMS);
	    URL obj = new URL("https://jsonplaceholder.typicode.com/posts");
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("userId", "a1bcdefgh");
	    postConnection.setRequestProperty("Content-Type", "application/json");
	    postConnection.setDoOutput(true);
	    OutputStream os = postConnection.getOutputStream();
	    os.write(POST_PARAMS.getBytes());
	    os.flush();
	    os.close();
	    int responseCode = postConnection.getResponseCode();
	    System.out.println("POST Response Code :  " + responseCode);
	    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
	    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	            postConnection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = in .readLine()) != null) {
	            response.append(inputLine);
	        } in .close();
	        // print result
	        System.out.println(response.toString());
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
	}
	/*
	 * public void createPost() { String url =
	 * "https://jsonplaceholder.typicode.com/posts";
	 * 
	 * // create headers HttpHeaders headers = new HttpHeaders(); // set
	 * `content-type` header headers.setContentType(MediaType.APPLICATION_JSON); //
	 * set `accept` header
	 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	 * 
	 * // create a map for post parameters Map<String, String> map = new
	 * HashMap<>(); map.put("userId", "1"); map.put("title",
	 * "Introduction to Spring Boot"); map.put("body",
	 * "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications."
	 * );
	 * 
	 * // build the request HttpEntity<Map<String, String>> entity = new
	 * HttpEntity<>(map, headers);
	 * 
	 * // send POST request ResponseEntity<Post> response =
	 * this.restTemplate.postForEntity(url, entity, Post.class);
	 * 
	 * // check response status code if (response.getStatusCode() ==
	 * HttpStatus.CREATED) { response.getBody(); } else {
	 * 
	 * } }
	 * 
	 * public void createJiraTicket(String description) {
	 * 
	 * final String uri = "https://jira.oraclecorp.com/jira/rest/api/2/issue/";
	 * String url = "https://jsonplaceholder.typicode.com/posts/{id}";
	 * 
	 * // create headers HttpHeaders headers = new HttpHeaders(); // set `accept`
	 * header
	 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); //
	 * set custom header headers.set("x-request-source", "desktop");
	 * 
	 * // build the request HttpEntity request = new HttpEntity(headers);
	 * 
	 * // use `exchange` method for HTTP call ResponseEntity<Post> response =
	 * this.restTemplate.exchange(url, HttpMethod.GET, request, Post.class, 1);
	 * if(response.getStatusCode() == HttpStatus.OK) { return response.getBody(); }
	 * else { return null; }
	 * 
	 * }
	 */}

package excelToJira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PortalSmokeTests {

	public static String testcaseNumber;
	public static String createdBy;
	public static String scenarioDescription;
	public static XSSFSheet spreadsheet;
	public static String tempDescription;

	public static void main(String[] args) {
		try {
			
			//POSTRequest();
			//MyGETRequest();
			FileInputStream fis = new FileInputStream(
					new File("C:\\Users\\nmadipel.ORADEV\\Desktop\\PORTAL Smoke Test - 20.2.0.0.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			spreadsheet = workbook.getSheet("Non-Term  Smoke Tests");

			int columns = spreadsheet.getRow(0).getLastCellNum();
			int rows = spreadsheet.getLastRowNum();

			for (int i = 1; i < rows; i++) {

				scenarioDescription = spreadsheet.getRow(i).getCell(11).getStringCellValue();
				testcaseNumber = spreadsheet.getRow(i).getCell(0).getStringCellValue();
				//scenarioDescription = spreadsheet.getRow(i).getCell(9).getStringCellValue();
				boolean module=true;	
				
				int j;
				boolean logDesc=true;
				for (j = i + 1; j < rows; j++) {
					
					if(module) {
						if(scenarioDescription.length()==0){
							module=false;
						}
					}
					tempDescription = spreadsheet.getRow(j).getCell(11).getStringCellValue();
					//tempDescription = spreadsheet.getRow(j).getCell(9).getStringCellValue();
					if (tempDescription == null || tempDescription.length() == 0) {
						if(logDesc) {
							System.out.println(
									"++++++++++++++++++ "+testcaseNumber +":"+scenarioDescription+" +++++++++++++++++");
							logDesc=false;
						}
						
						String step = spreadsheet.getRow(j).getCell(14).getStringCellValue();
						String expectedResult = spreadsheet.getRow(j).getCell(15).getStringCellValue();
						//String step = spreadsheet.getRow(j).getCell(12).getStringCellValue();
						//String expectedResult = spreadsheet.getRow(j).getCell(13).getStringCellValue();
						String testData = spreadsheet.getRow(j).getCell(16).getStringCellValue();
						if (step.length() > 1) {
							System.out.println(step + " :: " + expectedResult +" ::  "+testData);
							
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
		
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new  InetSocketAddress("www-proxy-hqdc.us.oracle.com", 80));
		
		/*
		 * String name = "naresh.madipelly@oracle.com"; String password =
		 * "Snehith@45@7";
		 * 
		 * String authString = name + ":" + password; System.out.println("auth string: "
		 * + authString); byte[] authEncBytes
		 * =Base64.encodeBase64(authString.getBytes()); String authStringEnc = new
		 * String(authEncBytes); System.out.println("Base64 encoded auth string: " +
		 * authStringEnc);
		 */
		 
		
		
	    URL urlForGetRequest = new URL("https://postman-echo.com/get?foo1=bar1&foo2=bar2");
	    String readLine = null;
	    
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    //conection.setRequestProperty("Authorization", "Basic "+authStringEnc);
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty("Content-Type", "application/json");
	    conection.setRequestProperty("Accept", "application/json");
	    conection.setAllowUserInteraction(true);
	    conection.setConnectTimeout(10000);
	    conection.setDoOutput(true);
	    conection.connect();
	    int responseCode = conection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        
	        System.out.println("JSON String Result " + response.toString());
	        
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
	}
	public static void POSTRequest() throws IOException {
	    final String POST_PARAMS = "{\r\n" + 
	    		"    \"fields\": {\r\n" + 
	    		"       \"project\":\r\n" + 
	    		"       {\r\n" + 
	    		"          \"id\": \"91102\"\r\n" + 
	    		"       },\r\n" + 
	    		"       \"summary\": \"No REST for the Wicked.12\",\r\n" + 
	    		"       \"description\": \"Creating of an issue using ids for projects and issue types using the REST API\",\r\n" + 
	    		"       \"assignee\": {\r\n" + 
	    		"          \"name\": \"kevin.lane@oracle.com\"\r\n" + 
	    		"       },\r\n" + 
	    		"       \"issuetype\": {\r\n" + 
	    		"        \r\n" + 
	    		"          \"name\": \"Test\"\r\n" + 
	    		"       }\r\n" + 
	    		"   }\r\n" + 
	    		"}";
	    System.out.println(POST_PARAMS);
	    URL obj = new URL("https://jira.oraclecorp.com/jira/rest/api/2/issue/");
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestProperty("Authorization", "Basic bmFyZXNoLm1hZGlwZWxseUBvcmFjbGUuY29tOlNuZWhpdGhANDVANw==");
	    postConnection.setRequestMethod("POST");
	    //postConnection.setRequestProperty("userId", "a1bcdefgh");
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
	       System.out.println(response.toString());
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
	}
}

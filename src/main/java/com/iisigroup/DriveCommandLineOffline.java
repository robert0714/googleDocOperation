package com.iisigroup;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Driver;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
 





import java.io.IOException;
import java.util.Collections;
public class DriveCommandLineOffline {
	private static String CLIENT_ID = "492199222059-dj5aqavlc2nmo9ucrm40gop4q7ip0b1q.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "YfW75sAK8wfES0CRUv34jMA5";

	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	public static void mainq(String[] args) throws IOException {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
				Arrays.asList(DriveScopes.DRIVE)).setAccessType("offline")
				.setApprovalPrompt("force").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
				.build();
		System.out
				.println("Please open the following URL in your browser then type the authorization code:");
		System.out.println("  " + url);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();
		
		
		GoogleTokenResponse response = flow.newTokenRequest(code)
				.setRedirectUri(REDIRECT_URI).execute();
		GoogleCredential credential = new GoogleCredential()
				.setFromTokenResponse(response);

		// Create a new authorized API client
		Drive service = new Drive.Builder(httpTransport, jsonFactory,
				credential).build();

		// Insert a file
		File body = new File();
		body.setTitle("My document 03");
		body.setDescription("A test document");
		body.setMimeType("text/plain");

		java.io.File fileContent = new java.io.File("Z:/ISWork/EclipseWorkspace/workspace_441/test/src/com/iisigroup/document.txt");
		FileContent mediaContent = new FileContent("text/plain", fileContent);

		File file = service.files().insert(body, mediaContent).execute();
		System.out.println("File ID: " + file.getId());
	}
	/** Provide the ID of the map you wish to read. */
	  private static final String MAP_ID = "YOUR_MAP_ID_HERE";

	  /**
	   * Be sure to specify the name of your application. If the application name is {@code null} or
	   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	   */
	  private static final String APPLICATION_NAME = "Google-MapsEngineApiSample/1.0";

	  /** Global instance of the HTTP transport. */
	  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	  /** Global instance of the JSON factory. */
	  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	  /** Authorizes the installed application to access user's protected data. 
	 * @throws IOException */
	  private static Credential authorize() throws IOException  {		  
	    GoogleClientSecrets.Details installedDetails = new GoogleClientSecrets.Details();
	    installedDetails.setClientId(CLIENT_ID);
	    installedDetails.setClientSecret(CLIENT_SECRET);

	    GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
	    clientSecrets.setInstalled(installedDetails);

	    // Set up a location to store retrieved credentials. This avoids having to ask for authorization
	    // every time the application is run
	    FileDataStoreFactory credentialStore = new FileDataStoreFactory(
	        new java.io.File(System.getProperty("user.home"), ".credentials/mapsengine.json"));

	    //credentials檔案建立起來才能更自動執行
	    
	    // Set up the authorization flow.
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        HTTP_TRANSPORT,
	        JSON_FACTORY,
	        clientSecrets,
	        Collections.singleton("https://www.googleapis.com/auth/drive"))
	          .setDataStoreFactory(credentialStore)
	          .build();
	    
//	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//	    		HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET,
//				Arrays.asList(DriveScopes.DRIVE)).setAccessType("offline")
//				.setApprovalPrompt("auto").build();

	    // Authorize this application.
	    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
	        .authorize("user");
	  }

	  public static String uploadSampleFile(final Drive service){
		  String fileId =null;
		  try {
			  	String mimeType ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
				// Insert a file
				File body = new File();
				body.setTitle("My document 03");
				body.setDescription("A test document");
//				body.setMimeType("text/plain");
				body.setMimeType(mimeType);
				
				
				java.io.File fileContent = new java.io.File("Z:/ISWork/EclipseWorkspace/workspace_441/GoogleDocPlay/src/main/java/com/iisigroup/JAVA處ManualCR_評估準則_查核表單.xlsx");
				FileContent mediaContent = new FileContent(mimeType, fileContent);

				File file = service.files().insert(body, mediaContent).execute();
				file.setWritersCanShare(true);
				
				
				User sharingUser = file.getSharingUser();
				System.out.println("SharingUser: " + sharingUser);
				
				final User newUser = new User();
				newUser.setEmailAddress("iisi.sonar@gmail.com");
				file.setSharingUser( newUser );
				
				List<Permission> persmissionList = file.getPermissions();
				if(persmissionList != null ){
					for(Permission pm : persmissionList){
						System.out.println("pm: " + pm);
					}
				}
				//https://developers.google.com/drive/v2/reference/permissions/insert
				fileId =file.getId() ;
				com.google.api.services.drive.model.Permission content=new com.google.api.services.drive.model.Permission();
				content.setRole("writer");
				content.setType("anyone");
				content.setEmailAddress("iisi.sonar@gmail.com");
				content.setFactory(JSON_FACTORY);
				 
				service.permissions().insert(fileId, content);
				System.out.println("File ID: " + file.getId());

		    } catch (IOException e) {
		    	 System.out.println("error");
		    	 System.out.println(e.getMessage());
		    } catch (Throwable t) {
		    	t.printStackTrace();
		    }
		  return fileId;
	  }
	  public static void main(String[] args) throws IOException {	   
	    	
	    	// Authorize this application to access the user's data.
	    	Credential credential = authorize();

	    	// Create a new authorized API client
			final Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					credential).build();

			final	String fileId = uploadSampleFile(service);
			
	  }
}

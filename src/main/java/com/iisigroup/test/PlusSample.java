package com.iisigroup.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.api.client.extensions.java6.auth.oauth2 .AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

public class PlusSample { 
	public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException {
		String APPLICATION_NAME = "DriverEditSample";		 

		// Set up the HTTP transport and JSON factory
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		// Load client secrets
		Credential credential = authorize(jsonFactory, httpTransport);

		 	 
		// Set up the main Google+ class
		Drive plus = new Drive.Builder(httpTransport, jsonFactory, credential)
		    .setApplicationName(APPLICATION_NAME)
		    .build();

		// Make a request to access your profile and display it to console
		FileList fileList = plus.files().list().execute();
		
		 
	}
	private static Credential authorize(final JsonFactory  JSON_FACTORY , final HttpTransport httpTransport) throws IOException, URISyntaxException  {
		java.io.File DATA_STORE_DIR =
			      new java.io.File("Z:");
			FileDataStoreFactory dataStoreFactory =new FileDataStoreFactory(DATA_STORE_DIR);
		
			// load client secrets
		  GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				  
		      new InputStreamReader(PlusSample.class.getResourceAsStream("/client_secrets.json")));
		  
		  // set up authorization code flow
		  GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		      httpTransport, JSON_FACTORY, clientSecrets,
		      Collections.singleton(DriveScopes.DRIVE)).setDataStoreFactory(
		      dataStoreFactory).build();
		  
		  // authorize
		  return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		}
}

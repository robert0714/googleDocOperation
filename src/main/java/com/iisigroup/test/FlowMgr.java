package com.iisigroup.test;

import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

public class FlowMgr {
	private static String CLIENT_ID = "492199222059-kstkf9nc3covfqtn1el486celp409q3l.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "yyUBIrGfxr4HPvdIlouRBSIy";

	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	
	public GoogleAuthorizationCodeFlow getFlow(){
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
				Arrays.asList(DriveScopes.DRIVE)).setAccessType("online")
				.setApprovalPrompt("auto").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
				.build();
		return flow;
	}
	public static void main(String[] args) throws IOException {
		 final GoogleAuthorizationCodeFlow flow = new FlowMgr().getFlow();
		 String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
					.build();
		
		 System.out.println(url);
		 
		 
	}
}

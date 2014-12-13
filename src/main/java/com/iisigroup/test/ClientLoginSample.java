package com.iisigroup.test;

import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.lang.System;

/**
 * https://code.google.com/p/google-api-java-client/wiki/Samples
 * 
 * **/
public class ClientLoginSample {
	public static void main(String[] args) throws IOException {
		// HttpTransport used to send login request.
		HttpTransport transport = new NetHttpTransport();
		try {
			// authenticate with ClientLogin
			ClientLogin authenticator = new ClientLogin();
			authenticator.transport = transport;
			// Google service trying to access, e.g., "cl" for calendar.
			authenticator.authTokenType = "cl";
			authenticator.username = "iisi.sonar@gmail.com";
			authenticator.password = "0920301309";
			Response response = authenticator.authenticate();
			System.out.println("Authentication succeeded.");
			
			String authorizationHeaderValue = response.getAuthorizationHeaderValue();
			System.out.println("authorizationHeaderValue");
		} catch (HttpResponseException e) {
			// Likely a "403 Forbidden" error.
			System.err.println(e.getStatusMessage());
			throw e;
		}
	}
}
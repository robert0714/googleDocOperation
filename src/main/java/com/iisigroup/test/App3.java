package com.iisigroup.test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory; 
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Hello world!
 * 參考https://hc.apache.org/httpcomponents-client-4.3.x/quickstart.html
 * 而做出來的測試程式碼
 */
public class App3 {
private static  String url ="https://accounts.google.com/o/oauth2/auth?access_type=online&approval_prompt=auto&client_id=492199222059-dj5aqavlc2nmo9ucrm40gop4q7ip0b1q.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/drive";
	public static void main(String[] args) throws Exception {

			new App3().testMethod();

	}
	public void testMethod() throws ClientProtocolException, IOException{
		
	    	final CloseableHttpClient httpclient = getHttpClient();
	    	final HttpGet httpGet = new HttpGet(url);	    	
	    	final CloseableHttpResponse response1 = httpclient.execute(httpGet);
		try {
			System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    String content =EntityUtils.toString(entity1);
		    System.out.println(content);
		    EntityUtils.consume(entity1);
		} finally {
		    response1.close();
		}
		httpclient.close();
	}
	  

	public CloseableHttpClient getHttpClient() { 
		try {			
			SSLConnectionSocketFactory sslsf = getSSLConnectionSocketFactory();
			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
			return httpclient;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HttpClients.createDefault();
		} 
	} 
	public SSLConnectionSocketFactory getSSLConnectionSocketFactory()
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException,
			UnrecoverableKeyException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[]{
			    new X509TrustManager() {
			        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			            return null;
			        }
			        public void checkClientTrusted(
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			        public void checkServerTrusted(
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			    }
			};
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		        sslContext,new TrustingHostnameVerifier());
		return sslsf;
	}

	private static final class TrustingHostnameVerifier implements
			X509HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
		public void verify(String host, SSLSocket ssl) throws IOException {
			
			System.out.println("verify(String host, SSLSocket ssl) throws IOException");
		}
		public void verify(String host, X509Certificate cert)
				throws SSLException {
			// TODO Auto-generated method stub
			System.out.println("verify(String host, X509Certificate cert)");
		}
		public void verify(String host, String[] cns, String[] subjectAlts)
				throws SSLException {
			System.out.println("verify(String host, String[] cns, String[] subjectAlts)");
		}
	}
	public SSLEngine getSSLEngine()
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException,
			UnrecoverableKeyException, KeyManagementException {
		// Create/initialize the SSLContext with key material

		final char[] passphrase = "passphrase".toCharArray();

		// First initialize the key and trust material.
		KeyStore ksKeys = KeyStore.getInstance("JKS");
		ksKeys.load(new FileInputStream("testKeys"), passphrase);
		KeyStore ksTrust = KeyStore.getInstance("JKS");
		ksKeys.load(new FileInputStream("testTrust"), passphrase);

		// KeyManager's decide which key material to use.
		final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ksKeys, passphrase);

		// TrustManager's decide whether to allow connections.
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(ksTrust);

		final SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		// We're ready for the engine.
		final SSLEngine engine = sslContext.createSSLEngine();

		// Use as client
		engine.setUseClientMode(true);
		return engine;
	}
	
}

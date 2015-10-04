package com.example.graphsample;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.ksoap2.transport.KeepAliveHttpsTransportSE;
import org.ksoap2.transport.Transport;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;

public class IncidentGetListTask extends AsyncTask<String, String, String> {

    private IncidentGetListResult mResult;
    private MainActivity mainAct;

    public IncidentGetListTask (IncidentGetListResult mResult,MainActivity mn){
        this.mResult = mResult;
        mainAct = mn;
    }

    @Override
    protected String doInBackground(String... mParams) {
        String webServiceUrl =  "";
        
        calculate();
     return webServiceUrl;   
     }
    
    public void calculate() {
    	
    	  final String URL = "https://api.monitoredsecurity.com/SWS/incidents.asmx?wsdl";
          final String NAMESPACE = "https://www.monitoredsecurity.com/";
          final String METHOD_NAME = "IncidentGetList";
          final String SOAP_ACTION = "https://www.monitoredsecurity.com/IncidentGetList";//NAMESPACE+METHOD;
          
    	
    	
        //String SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit";
        //String METHOD_NAME = "CelsiusToFahrenheit";
        //String NAMESPACE = "http://www.w3schools.com/webservices/";
        //String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            //Request.addProperty("Celsius", 33);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            FakeX509TrustManager.allowAllSSL();
            
            HttpTransportSE transport = new HttpTransportSE(URL);
            
            
            
            //HttpTransportSE transport = new KeepAliveHttpsTransportSE(
            	//	URL, 443, "/rpc/soap/jirasoapservice-v2", 1000);
            

            transport.call(SOAP_ACTION, soapEnvelope);
            
            SoapFault error = (SoapFault)soapEnvelope.bodyIn;
        	System.out.println("Error message : "+error.toString());
        	
            SoapPrimitive  resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i("TAG", "Result Celsius: " + resultString.toString());
        } catch (Exception ex) {
            Log.e("TAG", "Error: " + ex.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            mResult.onReceiveResult(result);
        } else {
            mResult.onReceiveError();
        }
        super.onPostExecute(result);
    }

    public interface IncidentGetListResult {
        public void onReceiveResult(String strResponse);
        public void onReceiveError();
    }
    
    private static class FakeX509TrustManager implements X509TrustManager {
        private static TrustManager[] trustManagers;
        private final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return _AcceptedIssuers;
        }

        public static void allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                	System.out.println("verify : "+hostname);
                    return true;
                }
            });
            SSLContext context = null;
            if (trustManagers == null) {
                trustManagers = new TrustManager[] { new FakeX509TrustManager() };
            }
            try {
                context = SSLContext.getInstance("TLS");
                context.init(null, trustManagers, new SecureRandom());
            }
            catch (final NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            catch (final KeyManagementException e) {
                e.printStackTrace();
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        }

        @Override
        public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) {
        	System.out.println("checkClientTrusted : "+arg1);
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
        	System.out.println("checkServerTrusted : "+authType);
        }
    }
}

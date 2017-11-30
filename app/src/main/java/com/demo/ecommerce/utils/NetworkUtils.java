package com.demo.ecommerce.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HurlStack;
import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.helper.HelperInterface;
import com.demo.ecommerce.managers.CustomVolleyPostRequestWithTextPlain;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetworkUtils implements HelperInterface {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static String volleyResponse;

    public static boolean isConnected(Context context) {
        int connectivityType = getConnectivityType(context);
        if (connectivityType == TYPE_NOT_CONNECTED)
            return false;
        else
            return true;
    }

    public static HurlStack getHurlStack() {
        HurlStack hurlStack = new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {
                if ("https".equals(url.getProtocol())) {


                    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }};

                    SSLContext sc = null;
                    try {
                        sc = SSLContext.getInstance("SSL");
                        sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    } catch (KeyManagementException e) {
                        StackTraceWriter.printStackTrace(e);
                    } catch (NoSuchAlgorithmException e) {
                        StackTraceWriter.printStackTrace(e);
                    }
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                    try {
                        httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
                    } catch (Exception e) {
                        StackTraceWriter.printStackTrace(e);
                    }
                    return httpsURLConnection;
                } else if ("http".equals(url.getProtocol())) {
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) super.createConnection(url);
                    return httpsURLConnection;
                }
                return super.createConnection(url);
            }
        };

        return hurlStack;
    }

    public static HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }



    public static int getConnectivityType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return TYPE_NOT_CONNECTED;
    }


    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }

}


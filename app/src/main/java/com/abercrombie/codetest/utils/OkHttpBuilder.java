package com.abercrombie.codetest.utils;

import com.abercrombie.codetest.App;
import com.abercrombie.codetest.BuildConfig;
import com.abercrombie.codetest.R;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpBuilder {

  public static OkHttpClient createClient() {
    OkHttpClient client = null;
    SSLContext sslContext = null;

    try {
      initKeyStore();
      sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts(), null);

      client = new OkHttpClient.Builder()
          .sslSocketFactory(sslContext.getSocketFactory(),  (X509TrustManager)trustAllCerts()[0])
          .addInterceptor(getLogging())
          .readTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          .retryOnConnectionFailure(false)
          .hostnameVerifier((hostname, session) -> true)
          .build();
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | KeyManagementException e) {
      e.printStackTrace();
    }

    return client;
  }

  private static void initKeyStore()
      throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
    CertificateFactory cf = null;
    InputStream cert = null;
    Certificate ca = null;

    cf = CertificateFactory.getInstance("X.509");
    cert = App.getInstance()
        .getResources()
        .openRawResource(R.raw.mycert);

    ca = cf.generateCertificate(cert);
    cert.close();

    String keyStoreType = KeyStore.getDefaultType();
    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
    keyStore.load(null, null);
    keyStore.setCertificateEntry("ca", ca);

    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
    tmf.init(keyStore);
  }

  private static HttpLoggingInterceptor getLogging() {
    return new HttpLoggingInterceptor()
        .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
            : HttpLoggingInterceptor.Level.NONE);
  }

  private static TrustManager[] trustAllCerts() {
    return new TrustManager[] {
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
          }

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
          }
        }
    };
  }
}

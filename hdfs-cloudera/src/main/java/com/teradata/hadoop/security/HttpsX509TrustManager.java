package com.teradata.hadoop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class HttpsX509TrustManager implements X509TrustManager {

    protected static final Logger Log = LoggerFactory.getLogger(HttpsX509TrustManager.class);

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

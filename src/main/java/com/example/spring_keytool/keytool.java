package com.example.spring_keytool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Endpoint(id = "keytool")
public class keytool{

    @ReadOperation
    public String keytool() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        // Чтение хранилища сертификатов
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(new FileInputStream("/home/avenon/dhabits.jks"), "qwerty".toCharArray());

        HashMap<Integer, Certificate> certificates = new HashMap<>();
        // Загрузка набора сертификатов
        Enumeration aliases = keystore.aliases();
        int i = 0;
        while (aliases.hasMoreElements()) {
            String alias = (String) aliases.nextElement();
            Date certExpiryDate = ((X509Certificate) keystore.getCertificate(alias)).getNotAfter();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = new Date();
            long dateDiff = certExpiryDate.getTime() - today.getTime(); // получаем время в unix формате
            long expiresIn = dateDiff / (24 * 60 * 60 * 1000);
            certificates.put(i, new Certificate(alias, expiresIn));
            i++;
        }
        //System.out.println(certificates);
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        String gsonString = gson.toJson(certificates,gsonType);
        //System.out.println(gsonString);

        return gsonString;
    }
}
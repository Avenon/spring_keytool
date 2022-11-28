package com.example.spring_keytool;

public class Certificate {
    public String aliasName;
    public Long expirationInDays;

    public Certificate(String aliasName, Long expirationInDays) {
        this.aliasName = aliasName;
        this.expirationInDays = expirationInDays;
    }


    @Override
    public String toString() {
        return "Certificate{" +
                "aliasName='" + aliasName + '\'' +
                ", expirationInDays=" + expirationInDays +
                '}';
    }
}

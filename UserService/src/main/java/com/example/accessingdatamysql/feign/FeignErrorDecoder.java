package com.example.accessingdatamysql.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new IllegalArgumentException("Resurs nije pronađen (404).");
            case 500:
                return new IllegalStateException("Interna greška servera (500).");
            default:
                return new Exception("Neočekivana greška: " + response.status());
        }
    }
}
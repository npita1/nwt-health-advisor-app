package com.example.accessingdatamysql.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "RESERVATIONSERVICE", configuration = FeignClientConfiguration.class)
public interface ReservationInterface {
    @DeleteMapping(path = "/reservation/deleteUser/{userServiceId}")
    ResponseEntity<Map<String, String>> deleteUser(@PathVariable("userServiceId") Long userServiceId);
}

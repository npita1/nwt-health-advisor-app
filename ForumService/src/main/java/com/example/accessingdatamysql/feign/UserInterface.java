package com.example.accessingdatamysql.feign;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "USERSERVICE")
public interface UserInterface {

    @RequestMapping(method = RequestMethod.GET, value = "/nwt/doctors/{doctorID}")
    public DoctorInfoEntity getDoctorID(@PathVariable int doctorID);

}

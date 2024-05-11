package com.example.accessingdatamysql.feign;

import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;



import java.util.List;
import java.util.Map;

@FeignClient(name = "FORUMSERVICE")
public interface ForumInterface {
    @GetMapping(path="/forum/userCom/articles/doctor/{doctorId}")
    public Map<String, String> getTitleAndTextArticleDoctorId(@PathVariable long doctorId);


}

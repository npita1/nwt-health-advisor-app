package com.example.accessingdatamysql.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name="FORUMSERVICE")
public interface ForumInterface {
    @PostMapping(path="/forum/reservationCom/event/addArticle")
    public @ResponseBody ResponseEntity<String> addArticle1(@RequestParam("doctorId") Long doctorId,
                                                            @RequestParam("categoryId") Long categoryId,
                                                            @RequestParam("title") String title,
                                                            @RequestParam("text") String text,
                                                            @RequestParam("date") String date);
}

package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.exceptions.CategoryNotFoundException;
import com.example.accessingdatamysql.feign.UserInterface;

import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import com.example.accessingdatamysql.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Validated
@CrossOrigin
@RequestMapping(path="/forum")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    UserInterface userClient;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;



    @PostMapping(path="/addArticleClassic")
    public @ResponseBody ResponseEntity<String> addArticle (@Valid @RequestBody ArticleEntity article) {
        ArticleEntity newArticle = articleService.addArticle(article);
        return ResponseEntity.ok("Article added.");
    }
//    @PostMapping(path = "/addArticle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public @ResponseBody ResponseEntity<?> addArticleNew(
//            @RequestParam("doctorId") int doctorId,
//            @RequestParam("image") MultipartFile image,
//            @RequestPart("article") ArticleEntity article) {
//        // Spremanje slike u folder
//        String imagePath = null;
//        if (!image.isEmpty()) {
//            try {
//                byte[] bytes = image.getBytes();
//                Path path = Paths.get("images/" + image.getOriginalFilename());
//                Files.write(path, bytes);
//                imagePath = path.toString();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri spremanju slike.");
//            }
//        }
//
//        DoctorInfoEntity doctor = userClient.getDoctorID(doctorId);
//        if (doctor == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doktor s ID-om " + doctorId + " nije pronađen.");
//        }
//
//        // Provjera da li je doktor vec spasen u bazi foruma
//        DoctorInfoEntity forumDoctor = doctorInfoRepository.findByUserId(doctor.getUser().getId());
//        if (forumDoctor == null) {
//            forumDoctor = new DoctorInfoEntity();
//            UserEntity forumUser = new UserEntity();
//            forumUser.setUserServiceId(doctor.getUser().getId());
//            forumUser.setEmail(doctor.getUser().getEmail());
//            forumUser.setFirstName(doctor.getUser().getFirstName());
//            forumUser.setLastName(doctor.getUser().getLastName());
//            forumUser.setPassword(doctor.getUser().getPassword());
//            userRepository.save(forumUser);
//
//            forumDoctor.setUser(forumUser);
//            forumDoctor.setAbout(doctor.getAbout());
//            forumDoctor.setSpecialization(doctor.getSpecialization());
//            forumDoctor.setAvailability(doctor.getAvailability());
//            forumDoctor.setPhoneNumber(doctor.getPhoneNumber());
//            doctorInfoRepository.save(forumDoctor);
//        }
//        article.setImagePath(imagePath);
//        ArticleEntity savedArticle = articleService.addArticleDoctor(article, forumDoctor);
//
//        return ResponseEntity.ok(savedArticle);
//    }
@PostMapping(path = "/addArticle", consumes = "multipart/form-data")
public @ResponseBody ResponseEntity<?> addArticleNew(
        @RequestParam("doctorId") int doctorId,
        @RequestParam("image") @ModelAttribute MultipartFile image,
        @RequestParam("title") String title,
        @RequestParam("text") String text,
        @RequestParam("date") String date,
        @RequestParam("categoryId") Long categoryId

        ) {
    // Spremanje slike u folder
    String imagePath = null;
    if (!image.isEmpty()) {
        try {
            byte[] bytes = image.getBytes();
//            Path path = Paths.get(System.getProperty("user.dir") + "/uploads/"
//                    + image.getOriginalFilename());
//            Files.write(path, bytes);
//            imagePath = path.toString();
            Path path = Paths.get("uploads/" + image.getOriginalFilename());
            Files.write(path, bytes);
            imagePath = "/uploads/" + image.getOriginalFilename(); // Relativna putanja
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri spremanju slike: " + e.getMessage());
        }
    }
    if (categoryId == null ) {
        return ResponseEntity.badRequest().body("Moraš odabrati kategoriju");
    }
    DoctorInfoEntity doctor = userClient.getDoctorID(doctorId);
    if (doctor == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doktor s ID-om " + doctorId + " nije pronađen.");
    }

    // Provjera da li je doktor vec spasen u bazi foruma
    DoctorInfoEntity forumDoctor = doctorInfoRepository.findByUserServiceId(doctor.getUser().getId());
    if (forumDoctor == null) {
        forumDoctor = new DoctorInfoEntity();
        UserEntity forumUser = new UserEntity();
        forumUser.setUserServiceId(doctor.getUser().getId());
        forumUser.setEmail(doctor.getUser().getEmail());
        forumUser.setFirstName(doctor.getUser().getFirstName());
        forumUser.setLastName(doctor.getUser().getLastName());
        forumUser.setPassword(doctor.getUser().getPassword());
        userRepository.save(forumUser);

        forumDoctor.setUser(forumUser);
        forumDoctor.setAbout(doctor.getAbout());
        forumDoctor.setSpecialization(doctor.getSpecialization());
        forumDoctor.setAvailability(doctor.getAvailability());
        forumDoctor.setPhoneNumber(doctor.getPhoneNumber());
        forumDoctor.setImagePath(doctor.getImagePath());
        doctorInfoRepository.save(forumDoctor);
    }
    // Učitajte kategoriju
    Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);
    if (categoryOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kategorija s ID-om " + categoryId + " nije pronađena.");
    }
    CategoryEntity category = categoryOpt.get();

    // Kreirajte članak
    ArticleEntity article = new ArticleEntity();
    article.setImagePath(imagePath);
    article.setTitle(title);
    article.setText(text);
    article.setDate(date);
    article.setDoctor(forumDoctor);
    article.setCategory(category);

   ArticleEntity savedArticle = articleService.addArticleDoctor(article, forumDoctor);

    return ResponseEntity.ok(savedArticle);
}




    @GetMapping(path="/allArticles")
    public @ResponseBody Iterable<ArticleEntity> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(path="/articles/{articleId}")
    public @ResponseBody ArticleEntity getArticle(@PathVariable long articleId) {
        ArticleEntity article = articleService.getArticleById(articleId);

        if (article == null) {
            throw new ArticleNotFoundException("Not found article by id: " + articleId);
        }

        return article;
    }

    @GetMapping(path="/articles/category/{category}")
    public @ResponseBody Iterable<ArticleEntity> getArticlesByCategory(@PathVariable String category) {
        return articleService.getByCategory(category);
    }

    @GetMapping(path="/articles/doctor/{doctorId}")
    public @ResponseBody Iterable<ArticleEntity> getArticleByDoctorId(@PathVariable long doctorId) {
        DoctorInfoEntity userServiceDoctor = userClient.getDoctorID((int)doctorId);
        DoctorInfoEntity forumServiceDoctor = doctorInfoRepository.findByUserId(userServiceDoctor.getUser().getId());
        Iterable<ArticleEntity> articles = articleService.getArticleByDoctorId(forumServiceDoctor.getId());

        if (articles == null) {

            throw new ArticleNotFoundException(" Not found article by doctor id: " + doctorId);
        }


        return articles;
    }
    @GetMapping(path="/userCom/articles/doctor/{doctorId}")
    public @ResponseBody Map<String, String> getTitleAndTextArticleDoctorId(@PathVariable long doctorId) {
        DoctorInfoEntity userServiceDoctor = userClient.getDoctorID((int)doctorId);
        DoctorInfoEntity forumServiceDoctor = doctorInfoRepository.findByUserId(userServiceDoctor.getUser().getId());
        Iterable<ArticleEntity> articles = articleService.getArticleByDoctorId(forumServiceDoctor.getId());

        if (articles == null) {
            throw new ArticleNotFoundException("Not found articles by doctor id: " + doctorId);
        }

        // Kljuc (naslov clanka) mora biti unikatan da bi se vratili svi clanci
        Map<String, String> articleMap = new HashMap<>();
        for (ArticleEntity article : articles) {
            articleMap.put(article.getTitle(), article.getText());
        }

        return articleMap;
    }

    @PostMapping(path="/reservationCom/event/addArticle")
    public @ResponseBody ResponseEntity<String> addArticleThroughEvent(@RequestParam("doctorId") Long doctorId,
                                              @RequestParam("categoryId") Long categoryId,
                                              @RequestParam("title") String title,
                                              @RequestParam("text") String text,
                                              @RequestParam("date") String date) {
        DoctorInfoEntity doctor = doctorInfoRepository.findById(doctorId)
                .orElseThrow(() -> new ArticleNotFoundException("Not found doctor by id: " + doctorId));
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Not found category by id: "+ categoryId));

        ArticleEntity article = new ArticleEntity();
        article.setDoctor(doctor);
        article.setCategory(category);
        article.setTitle(title);
        article.setText(text);
        article.setDate(date);

        articleService.addArticle(article);
        return ResponseEntity.ok("Article successfully added");
    }
    @DeleteMapping(path = "/deleteArticle/{articleId}")
    public @ResponseBody ResponseEntity<Map<String, String>>deleteArticle(@PathVariable Long articleId) {
        try {
            articleService.deleteArticle(articleId);
            Map<String, String> response = Map.of("message", "Članak uspješno obrisan.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
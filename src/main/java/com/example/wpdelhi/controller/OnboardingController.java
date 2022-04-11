package com.example.wpdelhi.controller;

import com.example.wpdelhi.config.RestTemplateConfig;
import com.example.wpdelhi.entity.UserInfo;
import com.example.wpdelhi.service.OnboardingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.catalina.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class OnboardingController implements InitializingBean, DisposableBean {

    @Value("${lecture.name}")
    String lecture;

    /**
     * CRUD APIs
     * Create: POST --> Input all the fields except Id -->Output is 201 OK
     * Read: GET ALL -->Returning all the users info -->
     * Read: GET by Id -->input id -->Output is UserInfo object
     * Delete: DELETE -->
     * UPDATE: PUT -->Modify the complete resource
     *
     */

    //Using HashMap as in-memory DB for storing the data
    Map<String, UserInfo> idToUserInfoMap = new HashMap<>();

    /**
     * It is method in InitializingBean
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        UserInfo userInfo = createUser("671 AVC GKP 8","abc@def.com",0551,"Ramesh");
        idToUserInfoMap.put(userInfo.getId(), userInfo);
        userInfo = createUser("29/148 WPNagar 8","abc@def.com",011,"Kalra");
        idToUserInfoMap.put(userInfo.getId(), userInfo);
        userInfo = createUser("A503 Collina 412106","abc@def.com",0211,"Shekhari");
        idToUserInfoMap.put(userInfo.getId(), userInfo);
        userInfo = createUser("Collina Apts","abc@def.com",020,"Lakhan");
        idToUserInfoMap.put(userInfo.getId(), userInfo);
        System.out.println("Reading application properties:-------->"+lecture);
    }

    private UserInfo createUser(String address, String email, Integer phoneNumber, String name) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAddress(address);
        userInfo.setEmail(email);
        userInfo.setPhoneNumber(phoneNumber);
        userInfo.setName(name);
        return  userInfo;
    }

    /**
     * It is method in DisposableBean
     * Free up the memory OR Shut Down Executor Service
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {

    }

    @RequestMapping(value = "/api/v1", method = RequestMethod.GET)
    @GetMapping("/users")
    public List<UserInfo>  getAllUsers (){
        log.debug("I am in getAllUsers");
        if(CollectionUtils.isEmpty(idToUserInfoMap)){
            throw new RuntimeException("No users found");
        }
        return idToUserInfoMap.values().stream().collect(Collectors.toList());

    }

    @GetMapping("/user")
    public UserInfo getUserById(@RequestParam("id")String id){
        UserInfo userInfo = idToUserInfoMap.get(id);
        if(userInfo == null){
            throw new RuntimeException("No user found for provided id");
        }
        return userInfo;
    }

    @Autowired
    OnboardingService onboardingService;

    @PostMapping("/user")
    public UserInfo createNewUser(@RequestBody UserInfo userInfo){
        log.info("Creating new user");
        idToUserInfoMap.put(userInfo.getId(), userInfo);
        log.info("New user created successfully");
        //onboardingService.createNewUser(userInfo);
        return userInfo;
    }

    @PutMapping("/user/{id}")
    public UserInfo updateAuser(@RequestBody UserInfo userInfo, @PathVariable(name="id") String id){
        log.info("Update user starts,id:"+id);
        if(idToUserInfoMap.isEmpty()){
            throw new RuntimeException("No users are present in DB");
        }
        UserInfo userInfo1 = idToUserInfoMap.get(id);
        if(Objects.isNull(userInfo1)){
            throw new RuntimeException("userInfo1 is null, given id not found");
        }
        userInfo1.setName(userInfo.getName());
        userInfo1.setEmail(userInfo.getEmail());
        userInfo1.setAddress(userInfo.getAddress());
        userInfo1.setPhoneNumber(userInfo.getPhoneNumber());
        idToUserInfoMap.put(id,userInfo1);
        log.info("Update user ends");
        return userInfo1;
    }

    @DeleteMapping("/user")
    public UserInfo deleteUser(@RequestParam("id") String id){
        UserInfo userInfo = idToUserInfoMap.get(id);
        if(Objects.isNull(userInfo)){
            throw  new RuntimeException("Requested id does not exist hence cannot delete.");
        }
        idToUserInfoMap.remove(id);
        return userInfo;
    }

    @Autowired
    RestTemplateConfig demoTemplate;


    /**
     * Lecture 10 2nd April 2022
     */
    @SneakyThrows
    @GetMapping(value = "/v1/fetchImage",produces = MediaType.IMAGE_JPEG_VALUE)
    public  byte[] fetchImage() throws IOException {
        //
        //OkHttpClient is one way to fetch data from another REST end point
        //RestTemplate is another way to fetch data
        //RestTemplate restTemplate = new RestTemplate();
        //return restTemplate.getForObject("https://via.placeholder.com/600/92c952",byte[].class);
        //RestTemplateConfig user defined class is yet another approach
        return demoTemplate.restTemplate().getForObject("https://via.placeholder.com/600/92c952",byte[].class);
        /*OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url("https://via.placeholder.com/600/92c952")
                .method("GET",null).build();
        Response response = client.newCall(request).execute();
        return  response.body().bytes();*/
    }
}

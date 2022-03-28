package com.example.wpdelhi.controller;

import com.example.wpdelhi.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class OnboardingController implements InitializingBean, DisposableBean {

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
     * Free up the memory OR
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {

    }

    //@RequestMapping(value = "/api/v1", method = RequestMethod.GET)
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

    @GetMapping
    public void createNewUser(UserInfo userInfo){

    }

}

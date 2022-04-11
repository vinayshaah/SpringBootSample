package com.example.wpdelhi.controller;

import com.example.wpdelhi.config.RestTemplateConfig;
import com.example.wpdelhi.entity.CreateUserRequestDTO;
import com.example.wpdelhi.entity.UserInfo;
import com.example.wpdelhi.service.OnboardingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class OnboardingControllerV2 implements InitializingBean, DisposableBean {


    @Autowired
    OnboardingService onboardingService;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @PostMapping(value = "/user2",produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> createAuser(@Valid @RequestBody CreateUserRequestDTO
                                                            createUserRequestDTO) throws Exception {
        log.info("createAuser() starts");
        ResponseEntity<UserInfo> re = new ResponseEntity<UserInfo>(
                onboardingService.createNewUser(createUserRequestDTO), HttpStatus.CREATED
        );
        return re;
    }
}

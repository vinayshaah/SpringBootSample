package com.example.wpdelhi.service;

import com.example.wpdelhi.dao.OnboardingDAO;
import com.example.wpdelhi.entity.CreateUserRequestDTO;
import com.example.wpdelhi.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OnboardingService {

    @Autowired
    OnboardingDAO onboardingDAO;

    /**
     * Passing a CreateUserRequestDTO object because the same will be passed from frontend
     * @param createUserRequestDTO
     * @return
     */
    public UserInfo createNewUser(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        System.out.println("In OnboardingService.createNewUser");
        //get the corresponding UserInfo from CreateUserRequestDTO
        //because we need to persist Id as well in the DB
        UserInfo userInfo = createUserRequestDTO.toUser();
        onboardingDAO.persistUserInfo(userInfo);
        return  userInfo;
    }
}

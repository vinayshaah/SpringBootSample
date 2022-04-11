package com.example.wpdelhi.entity;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    @NotNull
    private String name;
    @Email
    private  String email;
    @NotNull
    private Integer phoneNumber;
    @NotBlank
    private  String address;

    /**
     * This method will return UserInfo object corresponding to CreateUserRequestDTO
     * @return
     * We can also user Mapstruct for converting one object into another.
     */
    public UserInfo toUser(){
        //Shortcut of
        /*UserInfo object = new UserInfo();
        object.setPhoneNumber(this.getPhoneNumber());
        object.setAddress(this.getAddress());
        object.setName(this.getName());
        object.setEmail(this.getEmail()); */

        return UserInfo.builder().address(this.address).name(String.valueOf(this.phoneNumber))
                .email(this.email).phoneNumber(this.phoneNumber).build();
    }
}

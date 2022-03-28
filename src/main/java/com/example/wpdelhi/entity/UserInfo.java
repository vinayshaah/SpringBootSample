package com.example.wpdelhi.entity;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
//@AllArgsConstructor
public class UserInfo {

    @Setter(AccessLevel.NONE)
    public String id = UUID.randomUUID().toString();

    String name;

    String email;

    String address;

    Integer phoneNumber;
}

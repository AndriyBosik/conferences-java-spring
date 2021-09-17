package com.conferences.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Integer id;
    private String login;
    private String email;
    private String name;
    private String surname;
    private String imagePath;
    private String role;
}

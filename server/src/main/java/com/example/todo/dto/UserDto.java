package com.example.todo.dto;

import com.example.todo.entities.User;

public class UserDto {

    private String id;
    private String name;
    private String email;
    private String userpic;
    private String gender;
    private String locale;

    public static UserDto fromUser(User user) {
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setUserpic(user.getUserpic())
                .setGender(user.getGender())
                .setLocale(user.getLocale());
    }

    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUserpic() {
        return userpic;
    }

    public UserDto setUserpic(String userpic) {
        this.userpic = userpic;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserDto setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public UserDto setLocale(String locale) {
        this.locale = locale;
        return this;
    }
}

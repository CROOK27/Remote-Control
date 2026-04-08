package com.example.dto;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
@JsonView
@Data
public class UserDto{
    @JsonView(Views.ShortInfo.class)
    private Long id;
    @JsonView(Views.ShortInfo.class)
    private String email;
    @JsonView(Views.ShortInfo.class)
    private String firstName;
    @JsonView(Views.ShortInfo.class)
    private String lastName;
    @JsonView(Views.FullInfo.class)
    private String role;
    @JsonView(Views.FullInfo.class)
    private Long groupId;
    @JsonView(Views.ShortInfo.class)
    private String groupName;
    @JsonView(Views.FullInfo.class)
    private Boolean isActive;
}

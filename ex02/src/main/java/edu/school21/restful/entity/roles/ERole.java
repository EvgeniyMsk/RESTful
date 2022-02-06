package edu.school21.restful.entity.roles;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ERole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_TEACHER("ROLE_TEACHER");

    @JsonCreator
    ERole(String role) {

    }
}

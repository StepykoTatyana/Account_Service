package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Component
public class NewPassword {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @NotNull
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    private String new_password;

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public NewPassword(String new_password) {
        this.new_password = new_password;
    }

    public NewPassword() {
    }
}

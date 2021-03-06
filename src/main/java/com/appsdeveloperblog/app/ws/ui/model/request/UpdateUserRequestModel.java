package com.appsdeveloperblog.app.ws.ui.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserRequestModel {

    @NotNull(message = "First Name cannot be null")
    @NotBlank(message = "First Name cannot be empty")
    @Size(min=2, message="First Name must be less than 2 characters")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Size(min=2, message="Last Name must be less than 2 characters")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

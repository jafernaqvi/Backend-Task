package net.grandcentrix.assessment.smartenergy.api.user;

public class CreateUserRequest {

    private String name;
    private String password;
    public CreateUserRequest() { };
    public CreateUserRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}

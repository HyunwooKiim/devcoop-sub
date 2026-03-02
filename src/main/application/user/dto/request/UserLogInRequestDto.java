package main.application.user.dto.request;

public class UserLogInRequestDto {
    private String email;
    private String password;

    public UserLogInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

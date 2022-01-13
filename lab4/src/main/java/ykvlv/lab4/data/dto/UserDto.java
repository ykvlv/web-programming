package ykvlv.lab4.data.dto;

public class UserDto {
    private final String username;
    private final String password;
    private final String confirmPassword;

    public UserDto(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}

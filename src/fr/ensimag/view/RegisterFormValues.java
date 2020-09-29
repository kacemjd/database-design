package fr.ensimag.view;

public class RegisterFormValues {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String password;
    private String passwordConfirm;

    public RegisterFormValues(String fName, String lName, String mail, String city, String pwd, String pwdConf){
        setFirstName(fName);
        setLastName(lName);
        setEmail(mail);
        setCity(city);
        setPassword(pwd);
        setPasswordConfirm(pwdConf);
    }

    @Override
    public String toString() {
        return "RegisterFormValues{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}

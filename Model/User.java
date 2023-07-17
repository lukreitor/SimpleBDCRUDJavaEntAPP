package Model;

import DTO.Mappers.EGender;
import DTO.Mappers.ERole;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     * The user's CPF (Brazilian ID number).
     */
    private String CPF;
    /**
     * 
     * The user's username.
     */
    private String username;
    /**
     * 
     * The user's first name.
     */
    private String firstName;
    /**
     * 
     * The user's last name.
     */
    private String lastName;
    /**
     * 
     * The user's email address.
     */
    private String email;
    /**
     * 
     * The user's password.
     */
    private String password;
    /**
     * 
     * The user's birth date.
     */
    private LocalDate birthDate;
    /**
     * 
     * The user's gender.
     */
    private EGender gender;
    /**
     * 
     * The user's phone number.
     */
    private String phoneNumber;
    /**
     * 
     * The user's city.
     */
    private String city;
    /**
     * 
     * The user's state.
     */
    private String state;
    /**
     * 
     * The user's country.
     */
    private String Country;
    /**
     * 
     * The user's role (Client, Trainer, Manager).
     */
    private ERole role;
    /**
     * 
     * The user's registration date.
     */
    private Date registrationDate;

    // give me the constructors, getters and setters
    public User() {

    }

    // constructor with all the fields
    public User(String CPF, String username, String firstName, String lastName, String email, String password,
            String phoneNumber,
            LocalDate birthDate,
            EGender gender, String city, String state, String Country) {
        this.CPF = CPF;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.registrationDate = new Date();
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.Country = Country;
        this.role = ERole.CLIENT;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public EGender getGender() {
        return this.gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

}

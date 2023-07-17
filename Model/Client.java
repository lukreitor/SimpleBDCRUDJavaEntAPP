/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import java.util.List;

import DTO.Mappers.EGender;
import DTO.Mappers.EPaymentMethod;
import java.io.Serializable;

/**
 *
 * @author auxlu
 */
public class Client extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String occupation;
    private double income;
    private String cardholderName;
    private String cardNumber;
    private String expirationDate;
    private String securityCode;
    private String billingAddress;

    private double paymentAmount;

    private LocalDate nextPaymentDate;
    private LocalDate lastPaymentDate;

    private EPaymentMethod paymentMethod;

    // constructor
    public Client() {

    }

    // This is a constructor for the `Client` class that takes in several parameters
    // to initialize the
    // object's attributes. The parameters include personal information such as
    // name, email, phone
    // number, birth date, and gender, as well as financial information such as
    // occupation, income,
    // payment method, and billing information. The constructor calls the
    // constructor of the superclass
    // `User` to initialize the inherited attributes and then sets the remaining
    // attributes specific to
    // the `Client` class.

    public static void miniMaxSum(List<Integer> arr) {
        long sum = 0, max = Long.MIN_VALUE, min = Long.MAX_VALUE;

        for (int i = 0; i < arr.size(); i++) {
            long current = arr.get(i);
            sum += current;
            max = Math.max(max, current);
            min = Math.min(min, current);
        }

        long maxSum = sum - min;
        long minSum = sum - max;

        System.out.println(minSum + " " + maxSum);
    }

    public Client(String CPF, String username, String firstName, String lastName, String email, String password,
            String phoneNumber,
            LocalDate birthDate, EGender gender, String city, String state,
            String Country, String occupation, double income, String cardholderName, String cardNumber,
            String expirationDate, String securityCode, String billingAddress) {
        super(CPF, username, firstName, lastName, email, password, phoneNumber, birthDate, gender,
                city, state, Country);
        this.occupation = occupation;
        this.income = income;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.billingAddress = billingAddress;
    }

    // getters and setters
    
 

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public EPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDate nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "Client{" +
                "CPF='" + super.getCPF() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", phoneNumber='" + super.getPhoneNumber() + '\'' +
                ", birthDate=" + super.getBirthDate() +
                ", gender=" + super.getGender() +
                ", city='" + super.getCity() + '\'' +
                ", state='" + super.getState() + '\'' +
                ", country='" + super.getCountry() + '\'' +
                ", occupation='" + occupation + '\'' +
                ", income=" + income +
                ", cardholderName='" + cardholderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", lastPaymentDate=" + lastPaymentDate +
                ", nextPaymentDate=" + nextPaymentDate +
                ", paymentAmount=" + paymentAmount +
             
                '}';
    }

}

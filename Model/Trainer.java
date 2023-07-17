/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import java.util.Date;

import DTO.Mappers.EGender;
import java.io.Serializable;

/**
 *
 * @author auxlu
 */
public class Trainer extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The certification obtained by the staff member.
     */
    private String certification;

    /**
     * The area of expertise of the staff member.
     */
    private String speciality;

    /**
     * The name of the gym where the staff member works.
     */
    private String gym;

    /**
     * The hourly rate at which the staff member is paid.
     */
    private double hourlyRate;

    /**
     * The number of years of experience the staff member has.
     */
    private int yearsOfExperience;

    /**
     * The date on which the staff member was hired.
     */
    private Date hireDate;

    /**
     * The annual salary of the staff member.
     */
    private double salary;

    public Trainer() {

    }

    // This is a constructor for the Trainer class that takes in several parameters
    // to initialize the
    // object's attributes. It calls the constructor of the superclass (User) using
    // the `super` keyword
    // and passes in some of the parameters. It then sets the remaining attributes
    // of the Trainer
    // object using the other parameters passed in. Finally, it sets the hireDate
    // attribute to the
    // current date and the salary attribute to a default value of 3000.
    public Trainer(String CPF, String username, String firstName, String lastName, String email, String password,
            String phoneNumber,
            LocalDate birthDate,
            EGender gender, String city, String state, String Country, String certification,
            String speciality, String gym, double hourlyRate,
            int yearsOfExperience) {
        super(CPF, username, firstName, lastName, email, password, phoneNumber, birthDate, gender, city, state,
                Country);
        this.certification = certification;
        this.speciality = speciality;
        this.gym = gym;
        this.hourlyRate = hourlyRate;
        this.yearsOfExperience = yearsOfExperience;
        this.hireDate = new Date();
        this.salary = 3000;
    }
    
    
     public Trainer(String CPF, String username, String firstName, String lastName, String email, String password,
            String phoneNumber,
            LocalDate birthDate,
            EGender gender, String city, String state, String Country, int id, String certification,
            String speciality, String gym, double hourlyRate,
            int yearsOfExperience, Date hireDate, Double Salary) {
        super(CPF, username, firstName, lastName, email, password, phoneNumber, birthDate, gender, city, state,
                Country);
        this.certification = certification;
        this.speciality = speciality;
        this.gym = gym;
        this.hourlyRate = hourlyRate;
        this.yearsOfExperience = yearsOfExperience;
        this.hireDate = hireDate;
        this.salary = Salary;
    }
     
     public Double getSalary() {
         return salary;
     }
     
     public void setSalary(Double salary) {
         this.salary = salary;
     }
     
     public Date getHireDate() {
         return hireDate;
     
     }
     
     public void setHireDate(Date hireDate) {
         this.hireDate = hireDate;
     }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

 

}

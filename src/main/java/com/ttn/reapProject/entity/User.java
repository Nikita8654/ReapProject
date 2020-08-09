package com.ttn.reapProject.entity;

import com.ttn.reapProject.util.CryptoUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "First name must not be empty!")
    @NotNull
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    @NotNull
    private String lastName;
    private String fullName;

    @NotBlank
    @Column(unique = true)
    @Email(message = "Invalid email! Enter a valid email!")
    private String email;

    private String department;

    private String year;

    @NotBlank(message = "It's good not to have empty password!")
    @Size(min = 3, message = "Password must be atleast three characters long.")
    private String password;
    private String resetToken;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet = new HashSet<>(Arrays.asList(Role.USER));

    private Boolean active = true;
    private Integer goldShareable = 3;
    private Integer silverShareable = 2;
    private Integer bronzeShareable = 1;
    private Integer goldRedeemable = 0;
    private Integer silverRedeemable = 0;
    private Integer bronzeRedeemable = 0;

    private Integer points = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
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
        this.password = CryptoUtils.encrypt(password);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getGoldShareable() {
        return goldShareable;
    }

    public void setGoldShareable(Integer goldShareable) {
        this.goldShareable = goldShareable;
    }

    public Integer getSilverShareable() {
        return silverShareable;
    }

    public void setSilverShareable(Integer silverShareable) {
        this.silverShareable = silverShareable;
    }

    public Integer getBronzeShareable() {
        return bronzeShareable;
    }

    public void setBronzeShareable(Integer bronzeShareable) {
        this.bronzeShareable = bronzeShareable;
    }

    public Integer getGoldRedeemable() {
        return goldRedeemable;
    }

    public void setGoldRedeemable(Integer goldRedeemable) {
        this.goldRedeemable = goldRedeemable;
    }

    public Integer getSilverRedeemable() {
        return silverRedeemable;
    }

    public void setSilverRedeemable(Integer silverRedeemable) {
        this.silverRedeemable = silverRedeemable;
    }

    public Integer getBronzeRedeemable() {
        return bronzeRedeemable;
    }

    public void setBronzeRedeemable(Integer bronzeRedeemable) {
        this.bronzeRedeemable = bronzeRedeemable;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Role> getRolesAsList() {
        return new ArrayList<Role>(roleSet);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", year='" + year + '\'' +
                ", password='" + password + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", roleSet=" + roleSet +
                ", active=" + active +
                ", goldShareable=" + goldShareable +
                ", silverShareable=" + silverShareable +
                ", bronzeShareable=" + bronzeShareable +
                ", goldRedeemable=" + goldRedeemable +
                ", silverRedeemable=" + silverRedeemable +
                ", bronzeRedeemable=" + bronzeRedeemable +
                ", points=" + points +
                '}';
    }
}

package cares.innostark.com.cares.Models;

/**
 * Created by bcm on 2/23/2016.
 */
public class UserInfo {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String address;
    private String email;
    private String dateOfBirth;
    private int CustomerType;
    private Double InsurancesTotal;
    private Double ServicesItemsTotal;

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(int customerType) {
        CustomerType = customerType;
    }

    public Double getInsurancesTotal() {
        return InsurancesTotal;
    }

    public void setInsurancesTotal(Double insurancesTotal) {
        InsurancesTotal = insurancesTotal;
        //InsurancesTotal = String.valueOf(0.0);

    }

    public Double getServicesItemsTotal() {
        return ServicesItemsTotal;
    }

    public void setServicesItemsTotal(Double servicesItemsTotal) {
        ServicesItemsTotal = servicesItemsTotal;
        //ServicesItemsTotal = String.valueOf(0.0);
    }
}

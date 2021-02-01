package com.ikiugu.at_voice.api.dto;

import com.ikiugu.at_voice.api.model.Customer;

public class CustomerDto {
    private Customer customer;
    private String agentPhoneNumber;

    public CustomerDto() {
    }

    public CustomerDto(Customer customer, String agentPhoneNumber) {
        this.customer = customer;
        this.agentPhoneNumber = agentPhoneNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAgentPhoneNumber() {
        return agentPhoneNumber;
    }

    public void setAgentPhoneNumber(String agentPhoneNumber) {
        this.agentPhoneNumber = agentPhoneNumber;
    }
}
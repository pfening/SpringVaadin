/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nova.SpringVaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM customers", (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    public void update(Customer customer) {
        jdbcTemplate.update("UPDATE customers SET first_name=?, last_name=? WHERE id=?", customer.getFirstName(), customer.getLastName(), customer.getId());
    }
    
    public void create(Customer customer) {
        jdbcTemplate.update("INSERT INTO customers ( first_name, last_name ) VALUES (?,?)", customer.getFirstName(), customer.getLastName());
    }
    
    public void delete(Customer customer) {
        jdbcTemplate.update("DELETE FROM customers WHERE id=?", customer.getId());
    }

}
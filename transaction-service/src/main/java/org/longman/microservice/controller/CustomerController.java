package org.longman.microservice.controller;

import org.longman.entity.CustomerEntity;
import org.longman.microservice.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/customers")
@RestController
public class CustomerController {
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public void createCustomer(@RequestBody CustomerEntity customer) {
        customerMapper.insert(customer);
    }

    @GetMapping("/{id}")
    public CustomerEntity selectCustomer(@PathVariable Long id) {
        return customerMapper.selectById(id);
    }

    @GetMapping
    public List<CustomerEntity> selectAllCustomers() {
        return customerMapper.selectList(null);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer) {
        customer.setId(id);
        customerMapper.updateById(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerMapper.deleteById(id);
    }
}

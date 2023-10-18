package com.basic2.controller;

import com.basic2.entity.Customer;
import com.basic2.exception.BusinessException;
import com.basic2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerBasicRestController {

        @Autowired
        private CustomerRepository customerRepository;

        @PostMapping
        public Customer create(@RequestBody Customer customer) {
            return customerRepository.save(customer);
        }

        @GetMapping
        public List<Customer> getCustomers() {
                System.out.println("Test");
                return customerRepository.findAll();
        }

        @GetMapping("/{id}")
        public Customer getCustomer(@PathVariable Long id) {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);

            Customer customer = optionalCustomer.orElseThrow(() ->new BusinessException("User Not Found",
                    HttpStatus.NOT_FOUND));
                    return customer;
        }

        @GetMapping("/email/{email}")
        public Customer getCustomerByEmail(@PathVariable String email) {
                return customerRepository.findByEmail(email)
                        .orElseThrow(() -> new BusinessException("요청하신 email에 해당하는 유저가 없습니다",
                                HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
                Customer customer = customerRepository.findById(id)
                        .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));

                customerRepository.delete(customer);

                return ResponseEntity.ok(id + " Customer가 삭제 되었습니다.");

        }

@PutMapping("/{id}")
        public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetail) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Customer Not Found", HttpStatus.NOT_FOUND));
        customer.setName(customerDetail.getName());
        customer.setEmail(customerDetail.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        return updatedCustomer;
}


}

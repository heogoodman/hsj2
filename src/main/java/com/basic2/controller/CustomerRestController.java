package com.basic2.controller;


import com.basic2.dto.CustomerReqDTO;
import com.basic2.dto.CustomerResDTO;
import com.basic2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    @PostMapping
    public CustomerResDTO saveCustomer(@RequestBody CustomerReqDTO customerReqDTO){
        return customerService.saveCustomer(customerReqDTO);
    }

    @GetMapping("/{id}")
    public CustomerResDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerResDTO> getCustomers() {
            return customerService.getCustomers();
    }

    @PatchMapping("/{email}")
    public CustomerResDTO updateCustomer(@PathVariable String email, @RequestBody CustomerReqDTO customerReqDTO){
        return customerService.updateCustomer(email,customerReqDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(id + " Customer가 삭제처리 되었습니다.");
    }
}
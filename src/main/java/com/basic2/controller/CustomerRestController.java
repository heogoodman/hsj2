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
        return customerService.saveCustomer(customerReqDTO); //객체로날라오기때문에 @PathVariable불가임 
    }

    @GetMapping("/{id}")
    public CustomerResDTO getCustomerById(@PathVariable Long id) { //여기의메소드이름과 Service의메소드이름은 우리가지어준것
        return customerService.getCustomerById(id);   //서비스안의 findById(id) 는 부트에서 제공하는 메소드
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
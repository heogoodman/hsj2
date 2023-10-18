package com.basic2.service;

import com.basic2.dto.CustomerReqDTO;
import com.basic2.dto.CustomerResDTO;
import com.basic2.entity.Customer;
import com.basic2.exception.BusinessException;
import com.basic2.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerResDTO saveCustomer(CustomerReqDTO customerReqDTO) {
        Customer customer = modelMapper.map(customerReqDTO,Customer.class); // entity 타입으로 변환 db와직접적연결
        Customer savedCustomer = customerRepository.save(customer); // 그이후 레파지토리에서 엔티티객체를 넣어준다.
        return modelMapper.map(savedCustomer,CustomerResDTO.class); //레파지토리.save도 스프링에서 제공해줌
    }            // 그이후 다시 맵퍼로 엔티티를 res로 교체하고 받는다. 뷰로뿌려주기위한 용도

    @Transactional(readOnly = true)
    public CustomerResDTO getCustomerById(Long id) {
            Customer customerEntity = customerRepository.findById(id) // findById메소드자체리턴값이 엔티티임
                    .orElseThrow(() -> new BusinessException(id + " Customer Not Found", HttpStatus.NOT_FOUND));
                CustomerResDTO customerResDto = modelMapper.map(customerEntity,CustomerResDTO.class);
            return customerResDto;                  //Entity를 Res로
    }

    @Transactional(readOnly = true)
    public List<CustomerResDTO> getCustomers() {
            List<Customer> customerList = customerRepository.findAll();
            List<CustomerResDTO> customerResDTOList = customerList.stream()
                    .map(customer -> modelMapper.map(customer,CustomerResDTO.class))
                    .collect(toList());    //Entity를 Res로 그리고 다시 리스트로
            return customerResDTOList;
    }

    public CustomerResDTO updateCustomer(String email, CustomerReqDTO customerReqDTO) {
        Customer existCustomer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException(email + " Customer Not Found",HttpStatus.NOT_FOUND));
        //Dirty Checking 변경감지를 하므로 setter method만 호출해도 update query가 실행이 된다.
        existCustomer.setName(customerReqDTO.getName());
        return modelMapper.map(existCustomer, CustomerResDTO.class);
    }

    public void deleteCustomer(Long id) {
                Customer customer = customerRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(id + " Customer Not Found",HttpStatus.NOT_FOUND));
        customerRepository.delete(customer);
    }

}

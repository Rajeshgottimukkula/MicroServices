package com.example.accounts.service.impl;


import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourseNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
   AccountsRepository accountsRepository;
    @Autowired
   CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());

Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customer.getMobileNumber());
if(optionalCustomer.isPresent()){

    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
            +customerDto.getMobileNumber());



}



customer.setCreatedBy("Anonymous");
customer.setCreatedAt(LocalDateTime.now());

Customer savedCustomer=customerRepository.save(customer);

Accounts accounts=accountsRepository.save(createNewAccount(savedCustomer));






    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

     Customer customer=   customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourseNotFoundException("Customer","mobileNumber",mobileNumber));
     Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()->new ResourseNotFoundException("Account","customerId",customer.getCustomerId().toString()));

CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        return customerDto;
    }


    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
}

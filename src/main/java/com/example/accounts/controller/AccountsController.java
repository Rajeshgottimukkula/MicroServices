package com.example.accounts.controller;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountsController {


    @Autowired
    IAccountsService iAccountsService;


    @GetMapping("/sayHello")
    public String sayHello(){


        return "Helloo World";


    }


    @GetMapping("/fetch")
    ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber){

CustomerDto customerDto=iAccountsService.fetchAccount(mobileNumber);

return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){



iAccountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));




    }



}

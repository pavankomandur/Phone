package com.personal.phone.phonevalidation.controller;

import com.personal.phone.phonevalidation.model.PhoneNumber;
import com.personal.phone.phonevalidation.service.PhoneValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("phonebook")
public class PhoneController {

   @Value("${area.code.values}")
   private List<String> areaCodes;

   @Value("${pageLength}")
   private String pageLength;

   @Autowired
   private PhoneValidationService validationService;

   static final Logger log = LoggerFactory.getLogger(PhoneController.class);

   @RequestMapping(value = "/getSuggestions", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<PhoneNumber> getSuggestions(@RequestBody PhoneNumber phone)
   {
   PhoneNumber phoneObj=validationService.getSuggestions(phone.getPhoneNumber(),phone.getPageNo());
   return new ResponseEntity<PhoneNumber>(phoneObj,HttpStatus.OK);
   }




}

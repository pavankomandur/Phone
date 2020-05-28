package com.personal.phone.phonevalidation.service;

import com.personal.phone.phonevalidation.model.PhoneNumber;
import com.personal.phone.phonevalidation.utils.KeyPad;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneValidationServiceTest {

    @Autowired
    PhoneValidationService mockPhoneValidationService;


    @Test
    public void getListTest()
    {
        List<String> phoneNumbers=new ArrayList<String>();
        phoneNumbers.add("2245679876");
        phoneNumbers.add("2245679877");
        phoneNumbers.add("2245679878");
        phoneNumbers.add("2245679879");
        phoneNumbers.add("2245679880");
        List<String> mockResponse=mockPhoneValidationService.getList(phoneNumbers, KeyPad.getKeyPad());
        Assert.assertEquals(mockResponse.size(),163);
    }

    @Test
    public void getListTestWithAnotherScenarioTest()
    {
        List<String> phoneNumbers=new ArrayList<String>();
        phoneNumbers.add("2245667877");
        phoneNumbers.add("2245679877");
        phoneNumbers.add("2245679857");
        phoneNumbers.add("2245679874");
        phoneNumbers.add("2245679882");
        List<String> mockResponse=mockPhoneValidationService.getList(phoneNumbers, KeyPad.getKeyPad());
        Assert.assertEquals(mockResponse.size(),165);
    }
    @Test
    public void getListTestWithExceptionScenarioTest()
    {
        List<String> phoneNumbers=new ArrayList<String>();
        phoneNumbers.add("224566");
        phoneNumbers.add("224567");
        phoneNumbers.add("79857");
        List<String> mockResponse=mockPhoneValidationService.getList(phoneNumbers, KeyPad.getKeyPad());
        Assert.assertEquals(mockResponse,null);
    }

    @Test
    public void getSuggestionsTestWith10digitTest()
    {
        PhoneNumber mockResponse=mockPhoneValidationService.getSuggestions("6023140288",1);
        Assert.assertEquals(mockResponse.getCombinations().size(),5);
    }

    @Test
    public void getSuggestionsTestwith7digitTest()
    {
        PhoneNumber mockResponse=mockPhoneValidationService.getSuggestions("3140288",1);
        Assert.assertEquals(mockResponse.getCombinations().size(),5);
    }

    @Test
    public void attachAreaCodesTest()
    {
        List<String> mockResponse=mockPhoneValidationService.attachAreaCodes("3140288");
        Assert.assertEquals(mockResponse.size(),24);
    }

    @Test
    public void attachAreaCodesExceptionScenarioTest()
    {
        List<String> mockResponse=mockPhoneValidationService.attachAreaCodes("8023140288");
        Assert.assertEquals(mockResponse,null);
    }

    @Test
    public void getPerPageDetailsTest()
    {
        List<String> phoneNumbers=new ArrayList<String>();
        phoneNumbers.add("2245667877");
        phoneNumbers.add("2245679877");
        phoneNumbers.add("2245679857");
        phoneNumbers.add("2245679874");
        phoneNumbers.add("2245679882");
        List<String> mockResponse=mockPhoneValidationService.getPerPageDetails(phoneNumbers,1);
        Assert.assertEquals(mockResponse.size(),5);
    }

    @Test
    public void replaceRemainingDigitsTest()
    {
        String ph=mockPhoneValidationService.replaceRemainingDigits("8023140288",KeyPad.getKeyPad());
        Assert.assertEquals(ph,"T0AD1G0ATT");
    }

}

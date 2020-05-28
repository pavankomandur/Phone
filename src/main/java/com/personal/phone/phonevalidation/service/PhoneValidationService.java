package com.personal.phone.phonevalidation.service;

import com.personal.phone.phonevalidation.controller.PhoneController;
import com.personal.phone.phonevalidation.model.PhoneNumber;
import com.personal.phone.phonevalidation.utils.KeyPad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PhoneValidationService {

    static final Logger log = LoggerFactory.getLogger(PhoneValidationService.class);

    @Value("${area.code.values}")
    private List<String> areaCodes;

    @Value("${pageLength}")
    private String pageSize;

    @Autowired
    private PhoneNumber phoneNumberResponse;


    public List<String> getList(List<String> phoneNumbers,Map<String,String> keyPad)
    {

        List<String> suggestions = new ArrayList<String>();

        try {
            for (String phoneNo : phoneNumbers) {
                log.info("Phone Number " + phoneNo);
                if (phoneNo.length()<10) {
                    log.error("Invalid Length of Phone Number ");
                    return null;
                }
                int position = 0;
                String last_digit = phoneNo.substring(phoneNo.length() - 1);
                String[] phonedigits=phoneNo.split("");
                for (String digit : phonedigits) {
                    // Arrays.stream(phonedigits).map(x->{
                    if (keyPad.containsKey(digit))
                    {
                        String newPhoneNo="";
                        //Converting each digit to character with all combinations as per key pad
                        String[] possibilities = keyPad.get(digit).split("");
                        for (String ch : possibilities) {
                            log.info("digit going to replace is " + digit + " with " + ch);

                            newPhoneNo = phoneNo.substring(0, position)
                                    + ch
                                    + phoneNo.substring(position + 1);
                            log.info("converting each digit " + newPhoneNo);
                            // finally replacing the remaining digits with characters.
                            suggestions.add(replaceRemainingDigits(newPhoneNo,KeyPad.getKeyPad()));
                        }

                    }
                    position++;
                };

            }
        }
        catch(Exception ex)
        {
            log.error("Exception Occurred " + ex.getMessage());
            ex.printStackTrace(); // to be removed when moved to production
        }
        log.info("Final Phone Number is " + suggestions.toString());
        return suggestions;
    }

    public  String replaceRemainingDigits(String phNumber,Map<String,String> keyPad)
    {
        int index=0;
        for(String pd : phNumber.split("")) {
            if (keyPad.containsKey(pd)) {
                phNumber = phNumber.substring(0, index)
                        + keyPad.get(pd).split("")[0]
                        + phNumber.substring(index + 1);
            }
            index++;
        }
        log.info("After converting remaining digits " + phNumber);
        return phNumber;
    }


    public PhoneNumber getSuggestions(String phNumber, int pageNo)
    {
        try {
            List<String> totalPhoneNumbers = new ArrayList<String>();
            if (phNumber!=null && phNumber.length() > 7) {
                totalPhoneNumbers = getList(Arrays.asList(phNumber), KeyPad.getKeyPad());
            } else if (phNumber!=null && phNumber.length() <=7) {
                //if the phone number is 7 digits, then attaching all possible area codes to that as suggestions
                List<String> fullPhoneNumbers = attachAreaCodes(phNumber);
                totalPhoneNumbers = getList(fullPhoneNumbers, KeyPad.getKeyPad());

            }
            totalPhoneNumbers=totalPhoneNumbers.stream().distinct().collect(Collectors.toList());
            phoneNumberResponse.setCombinations(getPerPageDetails(totalPhoneNumbers, pageNo));
            phoneNumberResponse.setPageNo(pageNo);
            int totalPages = (totalPhoneNumbers.size() / Integer.parseInt(pageSize)) + 1;
            log.info("Total count is  " + totalPhoneNumbers.size());
            log.info("pages are " + totalPages);
            phoneNumberResponse.setPhoneNumber(phNumber);
            phoneNumberResponse.setTotalPages(totalPages);
            phoneNumberResponse.setTotalRecords(totalPhoneNumbers.size());
        }
        catch(Exception e)
        {
            log.error("Exception Occurred " + e.getMessage());
            e.printStackTrace(); // need to removed when moved to production
        }
        return phoneNumberResponse;
    }
    public List<String> attachAreaCodes(String phoneNumber)
    {
        List<String> finalNumbers=new ArrayList<String>();
        try {
            if (phoneNumber!=null && phoneNumber.length()==7) {
                for (String areaCode : areaCodes) {
                    finalNumbers.add(areaCode + phoneNumber);
                }
            }
            else
            {
                log.error("Invalid Phone Number : Length is not Matching ");
                return null;
            }
        }
        catch(Exception e)
        {
            log.error("Exception Occurred " + e.getMessage());
            e.printStackTrace(); // need to removed when moved to production
        }
        return finalNumbers;
    }

    /*
    This Method is used to prepare the data specific to particular page

     */
    public List<String> getPerPageDetails(List<String> phoneList,int pageNo) {
        System.out.println("Page Length from Properties are " + pageSize);
        try {
            int pageLength = Integer.parseInt(pageSize);
            int toIndex = 0;
            if (phoneList.size() > pageLength) {
                int fromIndex = ((pageNo * pageLength) - pageLength);
                if (fromIndex + pageLength > phoneList.size())
                    toIndex = phoneList.size();
                else
                    toIndex = fromIndex + pageLength;

                return phoneList.subList(fromIndex, toIndex);
            } else {
                return phoneList;
            }


        } catch (IndexOutOfBoundsException iob) {
            log.error("Index Out of Bound " + iob.getMessage());
            iob.printStackTrace(); // need to removed when moved to production
        }
        catch (Exception e) {
            log.error("Exception Occurred " + e.getMessage());
            e.printStackTrace(); // need to removed when moved to production
        }

        return null;
    }


}

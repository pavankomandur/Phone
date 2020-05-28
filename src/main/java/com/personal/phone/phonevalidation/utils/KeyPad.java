package com.personal.phone.phonevalidation.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


public class KeyPad {

    public static Map<String, String> getKeyPad()
    {
        Map<String, String> keyPad = new HashMap<String, String>();
        keyPad.put("2", "ABC");
        keyPad.put("3", "DEF");
        keyPad.put("4", "GHI");
        keyPad.put("5", "JKL");
        keyPad.put("6", "MNO");
        keyPad.put("7", "PQRS");
        keyPad.put("8", "TUV");
        keyPad.put("9", "WXYZ");
        return keyPad;
    }

}

package com.anton.AnyVisionJava.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchController extends BaseClass{

    @Autowired
    ObjectMapper objectMapper;

    public boolean queryValidation(String i_query){

        //Divide the query from the full url
        String[] fullQueryArray = i_query.split("\\?");
        String[] dividedQueryArray = fullQueryArray[1].split("&");

        // Term is an mandatory field
        // If the term is missing return error to client
        // Second if determines if the term is not empty
        if(!dividedQueryArray[0].contains("term"))
            return false;
        else if(dividedQueryArray[0].split("=").length == 1){
            return false;
        }
        return true;
    }

}

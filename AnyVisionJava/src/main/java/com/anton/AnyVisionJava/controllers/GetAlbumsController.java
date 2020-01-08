package com.anton.AnyVisionJava.controllers;

import org.springframework.stereotype.Component;

@Component
public class GetAlbumsController extends BaseClass {

    public boolean queryValidation(String i_query){

        //Divide the query from the full url
        String[] fullQueryArray = i_query.split("\\?");
        String[] dividedQueryArray;

        //Must check if the query contains the key id and entity
        if(!fullQueryArray[1].contains("id") || !fullQueryArray[1].contains("entity"))
            return false;

        dividedQueryArray = fullQueryArray[1].split("&");
//        id=6505474
//        entity=album
//        limit=2


        //Id and entity values validation
        for(int i = 0; i < dividedQueryArray.length; i++) {
            //Id validation
            if (dividedQueryArray[i].contains("id")) {
                String[] temp = dividedQueryArray[i].split("=");
                if (temp.length == 1)
                    return false;
            }

            if (dividedQueryArray[i].contains("entity")) {
                String[] temp = dividedQueryArray[i].split("=");
                if(temp.length == 1)
                    return false;
            }
        }
        return true;
    }

}

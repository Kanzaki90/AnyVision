package com.anton.AnyVisionJava.controllers;

import org.springframework.stereotype.Component;

@Component
public class GetArtistsController extends BaseClass{

    public boolean queryValidation(String i_query){

        //Divide the query from the full url
        String[] fullQueryArray = i_query.split("\\?");

        if(!fullQueryArray[1].contains("amgArtistId"))
            return false;

        // Retrieve the amgArtistId
        String[] amgArtistIdArray = fullQueryArray[1].split("=");

        // If there are no amgArtistId error
        if(amgArtistIdArray.length == 1)
            return false;

        return true;
    }

}

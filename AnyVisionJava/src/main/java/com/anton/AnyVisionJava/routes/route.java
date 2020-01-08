package com.anton.AnyVisionJava.routes;

import com.anton.AnyVisionJava.controllers.GetAlbumsController;
import com.anton.AnyVisionJava.controllers.GetArtistsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anton.AnyVisionJava.controllers.SearchController;

@RestController
public class route {

    // Dependency Injection
    @Autowired private SearchController search;
    @Autowired private GetAlbumsController searchAlbum;
    @Autowired private GetArtistsController searchArtist;

    @RequestMapping("/")
    public String index(){
        return "You're on the main page\n Please be advised You need to manually enter the following\n" +
                "urls: http://localhost:8080/search -> for the search \n" +
                "http://localhost:8080/getAlbums -> for 1 or more albums \n" +
                "http://localhost:8080/getArtists -> for the artists\n\n" +
                "There is options provided under /src/main/java/routes/route\n" +
                "for the search purposes (they're under a comment)";
    }

    @RequestMapping("/search")
    public String search(){

        //jack johnson
        String query = "https://itunes.apple.com/search?term=jack+johnson&limit=5";
//        String query = "https://itunes.apple.com/search?term=&limit=5";
//        String query = "https://itunes.apple.com/search?=jack+johnson&limit=5";



        if(!search.queryValidation(query)){
            return "An error ha occurred in the search method";
        }
            return search.baseSearch(query);

    }

    @RequestMapping("/getAlbums")
    public String getAlbums() {

        //Paul van Dyk

         String query = "https://itunes.apple.com/lookup?id=6505474&entity=album";

        // String query = "https://itunes.apple.com/lookup?id=6505474&entity=album&limit=2";
        // String query = "https://itunes.apple.com/lookup?id=&entity=album&limit=2";
        // String query = "https://itunes.apple.com/lookup?id=6505474&entity=&limit=2";


        if(!searchAlbum.queryValidation(query)){
            return "An error ha occurred in the getAlbum method method";
        }
        return searchAlbum.baseSearch(query);
    }

    @RequestMapping("/getArtists")
    public String getArtists(){
        String query = "https://itunes.apple.com/lookup?amgArtistId=224667,5723";
//        String query = "https://itunes.apple.com/lookup?amgArtistId=";

        if(!searchArtist.queryValidation(query)){
            return "An error ha occurred in the getArtists method method";
        }
        return searchArtist.baseSearch(query);
    }

}

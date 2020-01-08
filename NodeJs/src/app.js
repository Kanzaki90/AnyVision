const path = require('path');
const express = require('express');
const request = require('request');
const app = express();
var cors = require('cors')
app.use(cors());

const publicDirectory = path.join(__dirname, '../public');

const searchURL = "https://itunes.apple.com/search?";
const lookupURL = "https://itunes.apple.com/lookup?";



app.use(express.static(publicDirectory));


app.get("/", (req, res) => {

    res.render('index', null);
    console.log("here 1");
    console.log(req.query);
});

// search and multiple search
app.get("/search", (req, res) => {

    console.log("Inside search");

    // Separate the query
    let query = req.query;
    // console.log(req);
    console.log(query);

    // term is an mandatory field
    // If the term is missing return error to client

    if (typeof query.term === "undefined" || !query.term || query.term.length === 0) {
        return res.send({
            error: 'An error accured with the please enter a valid search'
        });
    }

    let url = searchURL + "term=" + query.term;

    // define an object which will contain artistID for the future search 
    // and the data which will return
    let returnObject = {};

    // If the search contains only one key value pair
    // encodeURI for proper url
    if (Object.entries(query).length === 1) {

        url = encodeURI(url);

    } else {
        // Remove the term key and value for convinience if there is more data in the query
        delete query.term;

        // Concat the rest of the query
        for (const property in query) {
            url += "&" + property + "=" + query[property];
        }
        url = encodeURI(url);
    }

    request({ url: url }, (error, response) => {

        if(error){
            return error;
        }

        const data = JSON.parse(response.body);
        returnObject["artistId"] = data.results[0].artistId; // artistID to ease it on the front end
        returnObject["data"] = data.results;
        // console.log(returnObject);
        return res.send(returnObject);

    });
});

// In order to use the following _endpoints the user MUST first enter the artist's name


//  min batch/limit size === 1
//  from this method we receive amgArtistId for the getArists endpoint
app.get("/getAlbums", (req, res) => {

    console.log("inside get albums");

    let query = req.query;
    // main fail cases -> no Id or entity provided 
    console.log(query);

    if (typeof query.id === "undefined" || !query.id || query.id.length === 0) {
        return res.send({ error: "An ID must be provided! Please fill in the artist in order to continue." });
    }

    if (typeof query.entity === "undefined" || !query.entity ||
        query.entity.length === 0 || query.entity !== "album") {

        return res.send({ error: "You must choose the ammount of albums You wish to receive." });
    }

    let url = lookupURL + "id=" + query.id + "&entity=" + query.entity;
    let returnObject = {};

    // Onli artist ID and the entity received
    if (Object.entries(query) === 2) {

        url = encodeURI(url);

    } else {
        // Remove the unnecessary key value pairs (ID & entity)
        delete query.id;
        delete query.entity;

        // Concat the rest of the query
        for (const property in query) {
            url += "&" + property + "=" + query[property];
        }

        url = encodeURI(url);
    }
    request({ url: url }, (error, response) => {

        if(error){
            return error;
        }

        const data = JSON.parse(response.body);

        returnObject["amgArtistId"] = data.results[0].amgArtistId; // allMusicguide IMG to ease it on the front end
        returnObject["data"] = data.results;
        return res.send(returnObject);

    });

});

// Final stage
app.get("/getArists", (req, res) => {
    let query = req.query;
    console.log(query);
    if (typeof query.amgArtistId === "undefined" || !query.amgArtistId || query.amgArtistId.length === 0) {
        return res.send({ error: "Please fill in the inittal data -> (I)Artist name (for the ID), (II) Ammount of albums You seek)." });
    }

    let url = encodeURI(lookupURL + "amgArtistId=" + query.amgArtistId);
    let returnObject = {};

    request({ url: url }, (error, response) => {

        if(error){
            return error;
        }

        const data = JSON.parse(response.body);

        console.log(data);

        returnObject["data"] = data.results;
        return res.send(returnObject);

    });

});

// start the server
app.listen(3000, () => {
    console.log('Server is up on port 3000');
});
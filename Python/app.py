from flask import Flask, render_template, request
from furl import furl
import requests


app = Flask(__name__)

@app.route("/")
def index():
    return render_template("index.html")


@app.route("/", methods=["POST"])
#
# for the Python API implementation the full
# server-response JSON is returned
def getvalue():

    income_data = request.form["data"]

    query = income_data.split("?")
    f = furl(income_data)

    # Search or multiple searches
    # If the term is missing return error to client
    if query[0] == "search":
        if "term" not in query[1] or len(f.args["term"]) == 0:
            return render_template("helper_index.html", data = "An error accured with the please enter a valid search")

        search_url = "https://itunes.apple.com/search?" + query[1]
        r = requests.get(url=search_url)

        # return full JSON data
        income_data = r.json()
        return render_template("helper_index.html", data=income_data)
    #
    # Album or multiple albums
    # Id & entity is mandatory
    if query[0] == "getAlbums":
        print("get albums")
        if "id" not in query[1] or len(f.args["id"]) == 0:
            return render_template("helper_index.html",
                                   data="An ID must be provided! Please fill in the artist in order to continue.")

        if "entity" not in query[1] or len(f.args["entity"]) == 0 or f.args["entity"] != "album":
            return render_template("helper_index.html",
                                   data="You must choose the ammount of albums You wish to receive")

        lookup_url = "https://itunes.apple.com/lookup?" + query[1]
        r = requests.get(url=lookup_url)

        # return full JSON data
        income_data = r.json()
        return render_template("helper_index.html", data=income_data)
    #
    #
    # Final stage
    if query[0] == "getArtists":
        if "amgArtistId" not in query[1] or len(f.args["amgArtistId"]) == 0:
            return render_template("helper_index.html",
                                   data = "Please fill in the inittal data -> (I)Artist name (for the ID), (II) Ammount of albums You seek).")

        lookup_url = "https://itunes.apple.com/lookup?" + query[1]
        r = requests.get(url=lookup_url)

        # return full JSON data
        income_data = r.json()
        return render_template("helper_index.html", data=income_data)

    return render_template("helper_index.html", data="ooopsie! error")


if __name__ == "__main__":
    app.run()

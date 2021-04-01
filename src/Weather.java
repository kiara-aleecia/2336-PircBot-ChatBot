// this class does the json parsing for the weather api

import com.google.gson.*;

public class Weather
{
    String description, country, city;
    double cTemp, minTemp, maxTemp, feels, wSpeed;

    public Weather(String json)
    {
        // make json object to parse
        JsonObject parse = JsonParser.parseString(json).getAsJsonObject();

        // assign json data to class vars
        this.description = parse.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        this.country = parse.get("sys").getAsJsonObject().get("country").getAsString();

        // assign json data to class vars
        this.city = parse.get("name").getAsString();
        this.cTemp = parse.get("main").getAsJsonObject().get("temp").getAsDouble();
        this.feels = parse.get("main").getAsJsonObject().get("feels_like").getAsDouble();
        this.minTemp = parse.get("main").getAsJsonObject().get("temp_min").getAsDouble();
        this.maxTemp = parse.get("main").getAsJsonObject().get("temp_max").getAsDouble();
        this.wSpeed = parse.get("wind").getAsJsonObject().get("speed").getAsDouble();

    }
}

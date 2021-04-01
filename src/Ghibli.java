// this class does the json parsing for the studio ghibli api

import org.json.simple.*;

public class Ghibli
{
    String title, director, year, description;

    public Ghibli(String json, String message)
    {
        String lower;

        JSONArray root = (JSONArray) JSONValue.parse(json);

        for(int i = 0; i < root.size(); i++)
        {
            JSONObject rootObj = (JSONObject) root.get(i);
            this.title = (String) rootObj.get("title");
            lower = this.title;
            message = message.trim();

            if(lower.equalsIgnoreCase(message))
            {
                this.director = (String) rootObj.get("director").toString();
                this.year = (String) rootObj.get("release_date").toString();
                this.description = (String) rootObj.get("description").toString();
                break;
            }
        }
    }
}

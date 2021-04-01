// this class will execute specific actions depending on the contents of the user's message

import org.jibble.pircbot.*;
import java.io.*;
import java.net.*;

public class MyBot extends PircBot {
    final String APP_ID = "d4db455a7026576443cb261244095b89";

    public MyBot() {
        // constructor for bot
        this.setName("CloudNine DeliveryService");
    }

    // either "weather [zipcode]" or "[zipcode] weather" still work
    public static boolean findZipcode(String i) {
        boolean isValid = false;

        try
        {
            Integer.parseInt(i);
            isValid = true;

        } catch (NumberFormatException ex)
        {
            ex.printStackTrace();

        }
        return isValid;
    }

    public void onMessage(String channel, String sender,
                          String login, String hostname, String message)
    {
        // weather api
        if (message.equalsIgnoreCase("weather") || message.contains("weather")
                || message.matches("weather") || message.startsWith("weather") ||
                message.endsWith("weather"))
        {
            String uInput[] = message.split(" ");

            Weather lWeather;
            String zCode, cCode, city;

            // get zipcode from user input
            if (findZipcode(uInput[0]))
            {
                zCode = uInput[0];
            }
            else
            {
                zCode = uInput[1];
            }

            try {
                lWeather = getWeather(zCode);
                cCode = lWeather.country;
                city = lWeather.city;

                lWeather.cTemp = (int) ((lWeather.cTemp - 273.15) * 1.8 + 32);
                lWeather.feels = (int) ((lWeather.feels - 273.15) * 1.8 + 32);
                lWeather.minTemp = (int) ((lWeather.minTemp - 273.15) * 1.8 + 32);
                lWeather.maxTemp = (int) ((lWeather.maxTemp - 273.15) * 1.8 + 32);

                sendMessage(channel, "The weather in " + city + ", " + cCode + ": " + lWeather.description
                        + " with a temperature of " +  lWeather.cTemp + "°F");
                sendMessage(channel, "Feels like: " + lWeather.feels + "°F");
                sendMessage(channel, "High: " + lWeather.maxTemp + "°F");
                sendMessage(channel, "Low: " + lWeather.minTemp + "°F");
                sendMessage(channel, "Wind Speed: " + lWeather.wSpeed + " mph");

            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("davinkibot", "Oops, try again. ");// + e.toString());
            }

        }

        // ghibli api
        if (message.equalsIgnoreCase("studio ghibli") || message.contains("studio ghibli")
                || message.matches("studio ghibli") || message.startsWith("studio ghibli") ||
                message.endsWith("studio ghibli"))
        {
            Ghibli movie;
            String iGhibli = message.toLowerCase();
            iGhibli = iGhibli.replace("studio ghibli", "");

            try
            {
                movie = getMovie(iGhibli);

                sendMessage(channel, movie.title + " (" + movie.year + "):");
                sendMessage(channel, "Plot: " + movie.description);
                sendMessage(channel, "Directed by: " + movie.director);


            } catch (IOException e)
            {
                e.printStackTrace();
                sendMessage("davinkibot", "Oops, try again. ");// + e.toString());
            }
        }

        // some generic greetings
        if ((message.equalsIgnoreCase("hello") || message.contains("hello")
                || message.matches("hello") || message.startsWith("hello") ||
                message.endsWith("hello")) || (message.equalsIgnoreCase("hey") || message.contains("hey")
                || message.matches("hey") || message.startsWith("hey") ||
                message.endsWith("hey")))
        {
            sendMessage(channel, "Hey " + sender + "! Now how about that WEATHER, am I right?");
        }

        // time response
        if ((message.equalsIgnoreCase("time") || message.contains("time")
                || message.matches("time") || message.startsWith("time") ||
                message.endsWith("time")))
        {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
            sendMessage(channel, "Did you maybe want to know about Studio Ghibli? Ask me about Studio Ghibli right now.");
        }

    }

    // http web request from ghibli api
    Ghibli getMovie(String movie) throws IOException
    {
        String gURL = "https://ghibliapi.herokuapp.com/films/"; // fix
        URL obj = new URL(gURL);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String iLine;
        StringBuilder response = new StringBuilder();

        // read whole output
        while ((iLine = in.readLine()) != null)
        {
            response.append(iLine);
        }

        in.close();

        conn.disconnect();

        return (new Ghibli(response.toString(), movie));
    }
    // http web request from weather api
    Weather getWeather(String zCode) throws IOException {
        String wURL = "http://api.openweathermap.org/data/2.5/weather?zip=" + zCode + "&appid=" + APP_ID;
        URL obj = new URL(wURL);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String iLine;
        StringBuilder response = new StringBuilder();

        // read whole output
        while ((iLine = in.readLine()) != null)
        {
            response.append(iLine);
        }

        in.close();

        conn.disconnect();

        return (new Weather(response.toString()));
    }
}

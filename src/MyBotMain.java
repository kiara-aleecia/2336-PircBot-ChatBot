//import org.jibble.pircbot.*;
// main bot class

public class MyBotMain
{

    public static void main(String[] args) throws Exception
    {

        // changed MyBot bot = new MyBot() to lisa!
        // Now start our bot up.
        MyBot lisa = new MyBot();

        // Enable debugging output.
        lisa.setVerbose(true);

        // Connect to the IRC server.
        lisa.connect("irc.freenode.net");

        // Join the #pircbot channel.
        lisa.joinChannel("#davinkibot");

        lisa.sendMessage("#davinkibot", "You can ask me for the weather using a zipcode or about a Studio Ghibli movie!");
        lisa.sendMessage("#davinkibot", "It'd be nice if you used: [zipcode] weather OR studio ghibli [movie]");



    }

}

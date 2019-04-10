package w1_e8_url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    private HashMap<Integer, Integer> wordCount = new HashMap<>();
    private URL url;

    private Main(String urlString)
    {
        setUrl(urlString);
        getWordLengths();
        printWordLengths();
    }

    private void getWordLengths()
    {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                saveWordLengths(line.length());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void printWordLengths()
    {
        for (Map.Entry<Integer, Integer> entry : wordCount.entrySet())
        {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void saveWordLengths(int length)
    {
        if (wordCount.containsKey(length))
        {
            int previousCount = wordCount.get(length);
            wordCount.put(length, ++previousCount);
        }
        else
        {
            wordCount.put(length, 1);
        }
    }

    private void setUrl(String urlString)
    {
        try
        {
            url = new URL(urlString);
        }
        catch(MalformedURLException e)
        {
            System.out.println("Not a valid URL.");
        }
    }

    public static void main(String[] args)
    {
        new Main("https://raw.githubusercontent.com/dwyl/english-words/master/words.txt");
    }
}

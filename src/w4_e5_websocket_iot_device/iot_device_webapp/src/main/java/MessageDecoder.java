import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<Message> 
{
    @Override
    public Message decode(String s) throws DecodeException
    {
        Gson gson = new Gson();
        Message message = gson.fromJson(s, Message.class);
        return message;
    }

    @Override
    public boolean willDecode(String s) 
    {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) 
    {

    }

    @Override
    public void destroy()
    {

    }
}
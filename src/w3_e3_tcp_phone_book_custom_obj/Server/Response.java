package w3_e3_tcp_phone_book_custom_obj.Server;

import java.io.Serializable;

public class Response implements Serializable
{
    private Friend friend;
    private final boolean found;

    public Response(boolean found)
    {
        this.found = found;
    }

    public Response(boolean found, Friend friend)
    {
        this.found = found;
        this.friend = friend;
    }

    public boolean getFound()
    {
        return found;
    }

    public Friend getFriend()
    {
        return friend;
    }
}

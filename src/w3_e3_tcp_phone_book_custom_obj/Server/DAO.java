package w3_e3_tcp_phone_book_custom_obj.Server;

import java.util.List;
import java.util.ArrayList;

public class DAO
{
    private final List<Friend> friends = new ArrayList<>();

    public DAO()
    {
        friends.add(new Friend("Gottfrid Öberg", "Östantorp Vinö 84", "040-5914578"));
        friends.add(new Friend("Oscar Månsson", "Simpnäs 83", "0175-2308731"));
        friends.add(new Friend("August Berg", "Korsträsk 25", "031-4365204"));
        friends.add(new Friend("Vanessa Holmgren", "Gamla Svedalavägen 83", "0320-7923161"));
        friends.add(new Friend("Denis Ek", "Koppramåla 88", "054-5068637"));
    }

    public Friend getFriend(String name)
    {
        for (Friend friend : friends)
        {
            if (friend.getName().equalsIgnoreCase(name))
            {
                return friend;
            }
        }

        return null;
    }
}

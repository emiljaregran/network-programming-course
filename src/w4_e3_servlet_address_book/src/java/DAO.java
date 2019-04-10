import java.util.ArrayList;
import java.util.List;

public class DAO
{
    private final List<Friend> friends = new ArrayList<>();

    public DAO()
    {
        friends.add(new Friend("Amela Sjöberg", "Idvägen 3, 360 73  LENHOVDA","0474-6692366"));
        friends.add(new Friend("Annie Åkesson", "Storgatan 34, 130 56  UTÖ", "070-2770858"));
        friends.add(new Friend("Edgar Isaksson", "Eriksbo Västergärde 47, 330 33  HILLERSTORP", "073-7328724"));
        friends.add(new Friend("Abdullahi Bergström", "Lemesjö 68, 280 72  KILLEBERG","0479-3427980"));
        friends.add(new Friend("Elvin Lundqvist", "Törneby 27, 182 89  DJURSHOLM", "0704-6648681"));
    }

    public String getFriend(String name)
    {
        for (Friend friend : friends)
        {
            if (friend.getName().equalsIgnoreCase(name))
            {
                return friend.getName() + "<br>" + friend.getAddress() + "<br>" + friend.getPhoneNumber();
            }
        }

        return "NOT_FOUND";
    }
    
    public String getAll()
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (Friend friend : friends)
        {
            stringBuilder.append(friend.getName());
            stringBuilder.append("<br>");
            stringBuilder.append(friend.getAddress());
            stringBuilder.append("<br>");
            stringBuilder.append(friend.getPhoneNumber());
            stringBuilder.append("<p><p>");
        }
        
        return stringBuilder.toString();
    }
}

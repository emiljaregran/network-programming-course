package week_3_exercise_6.Server;

public class Protocol
{
    private final DAO DAO = new DAO();
    private boolean initialized = false;

    public Object serve(String receivedMessage)
    {
        Object responseObject;

        if (!initialized)
        {
            responseObject = new Intro();
            initialized = true;
        }
        else
        {
            System.out.println("Received message: " + receivedMessage);
            Friend friend = DAO.getFriend(receivedMessage.trim());
            if (friend != null)
            {
                responseObject = new Response(true, friend);
                System.out.println("Sent: " + friend.getName());
            }
            else
            {
                responseObject = new Response(false);
                System.out.println("Sent: No friend with that name was found in the DAO.");
            }
        }

        return responseObject;
    }
}

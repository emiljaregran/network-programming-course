package w3_e5_tcp_riddles.Server;

public class RiddleProtocol
{
    private static final int WAITING = 0;
    private static final int SENTRIDDLE = 1;
    private static final int ANOTHER = 2;

    private static final int NUMRIDDLES = 3;

    private int state = WAITING;
    private int currentRiddle = 0;

    private String[] riddles = {"Which side of a cat always has the most fur?",
                                "Your mother and father have a child. It is not your brother and it is not your sister. Who is it?",
                                "Which kind of nuts are the softest nuts?"};

    private String[] answers = {"The outside",
                                "It's me",
                                "Donuts"};

    public String processInput(String clientMessage) {
        String serverResponse = null;

        if (state == WAITING)
        {
            serverResponse = riddles[currentRiddle];
            state = SENTRIDDLE;
        }
        else if (state == SENTRIDDLE)
        {
            if (clientMessage.equalsIgnoreCase(answers[currentRiddle]))
            {
                serverResponse = "That's correct! Want another? (y/n)";
            }
            else
            {
                serverResponse = "Wrong answer. Want another? (y/n)";
            }

            state = ANOTHER;
        }
        else if (state == ANOTHER)
        {
            if (clientMessage.equalsIgnoreCase("y"))
            {
                if (currentRiddle == (NUMRIDDLES - 1))
                {
                    currentRiddle = 0;
                }
                else
                {
                    currentRiddle++;
                }

                serverResponse = riddles[currentRiddle];
                state = SENTRIDDLE;
            }
            else
            {
                serverResponse = "Bye.";
                state = WAITING;
            }
        }

        return serverResponse;
    }
}

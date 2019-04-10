package w3_e8_tcp_chat.Server;

import java.util.Set;
import java.util.HashSet;

public class ChatServer
{
    private final Set<ChatUser> chatUsers = new HashSet<>();

    private ChatServer(int listeningPort)
    {
        new ConnectionListener(this, listeningPort);
    }

    void addChatUser(ChatUser chatUser)
    {
        chatUsers.add(chatUser);
        System.out.println("Added user " + chatUser.getNickname() + " to the chat.");

        chatUser.sendMessageToClient(getUserList());
        propagateMessage("JOIN:" + chatUser.getNickname());
    }

    void removeChatUser(ChatUser chatUser)
    {
        System.out.println("Removed user " + chatUser.getNickname() + " from the chat.");
        propagateMessage("LEAVE:" + chatUser.getNickname());
        chatUsers.remove(chatUser);
    }

    private String getUserList()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USERLIST");

        for (ChatUser chatUser : chatUsers)
        {
            stringBuilder.append(":").append(chatUser.getNickname());
        }

        return stringBuilder.toString();
    }

    boolean nicknameAlreadyExists(String nickname)
    {
        for (ChatUser chatUser : chatUsers)
        {
            if (chatUser.getNickname().equalsIgnoreCase(nickname))
            {
                return true;
            }
        }

        return false;
    }

    void propagateMessage(String message)
    {
        for (ChatUser chatUser : chatUsers)
        {
            chatUser.getSocketWriter().println(message);
        }
    }

    public static void main(String[] args)
    {
        new ChatServer(12345);
    }
}

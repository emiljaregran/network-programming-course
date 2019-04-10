package w3_e8_tcp_chat.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

class ChatProtocol
{
    private final ChatClient chatClient;
    private final ChatWindow chatWindow;
    private final JList<String> userList;

    ChatProtocol(ChatClient chatClient, ChatWindow chatWindow, JList<String> userList)
    {
        this.chatClient = chatClient;
        this.chatWindow = chatWindow;
        this.userList = userList;
    }

    void processMessage(String receivedMessage)
    {
        String[] message = receivedMessage.split(":", 2);

        switch (message[0])
        {
            case "ERR":
                printErrorMessage(message[1]);
                chatClient.setDisconnectedState();
                break;

            case "JOIN":
                addToUserList(message[1]);
                printJoinMessage(message[1]);
                break;

            case "LEAVE":
                removeFromUserList(message[1]);
                printLeaveMessage(message[1]);
                break;

            case "USERLIST":
                initializeUserList(receivedMessage);
                break;

            default:
                printChatMessage(receivedMessage);
        }
    }

    private void printErrorMessage(String errorMessage)
    {
        chatWindow.append(chatWindow.getTimestamp(), Color.WHITE, true);
        chatWindow.append(errorMessage + "\n", Color.RED, true);
    }

    private void printJoinMessage(String nickname)
    {
        chatWindow.append(chatWindow.getTimestamp(), Color.WHITE, true);
        chatWindow.append(nickname + " has joined the chat!\n", Color.MAGENTA, true);
    }

    private void printLeaveMessage(String nickname)
    {
        chatWindow.append(chatWindow.getTimestamp(), Color.WHITE, true);
        chatWindow.append(nickname + " has left the chat.\n", Color.RED, true);
    }

    private void initializeUserList(String userListMessage)
    {
        String[] users = userListMessage.substring(9).split(":");
        Arrays.sort(users);
        userList.setListData(users);
    }

    private void addToUserList(String nickname)
    {
        if (!nickname.equals(chatClient.getNickname()))
        {
            ListModel list = userList.getModel();
            Vector<String> newUserListContent = new Vector<>(list.getSize() + 1);

            for (int i = 0; i < list.getSize(); i++)
            {
                newUserListContent.add((String)list.getElementAt(i));
            }

            newUserListContent.add(nickname);
            Collections.sort(newUserListContent);
            userList.setListData(newUserListContent);
        }
    }

    private void removeFromUserList(String nickname)
    {
        ListModel list = userList.getModel();
        Vector<String> newUserListContent = new Vector<>(list.getSize() - 1);

        for (int i = 0; i < list.getSize(); i++)
        {
            String storedNickname = (String)list.getElementAt(i);
            if (!storedNickname.equals(nickname))
            {
                newUserListContent.add(storedNickname);
            }
        }

        userList.setListData(newUserListContent);
    }

    private void printChatMessage(String chatMessage)
    {
        chatWindow.append(chatWindow.getTimestamp(), Color.WHITE, true);
        chatWindow.append(chatMessage + "\n", Color.GREEN, false);
    }
}

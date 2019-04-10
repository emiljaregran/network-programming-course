import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/iot_devices/{device_id}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class DeviceEndpoint {
    private Session session;
    private static final Set<DeviceEndpoint> deviceEndpoints = new CopyOnWriteArraySet<>();
    private static final HashMap<String, String> devices = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("device_id") String device_id) throws IOException, EncodeException
    {
        this.session = session;
        deviceEndpoints.add(this);
        devices.put(session.getId(), device_id);

        Message message = new Message();
        message.setFrom(device_id);
        message.setContent("off");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException
    {
        if (deviceEndpoints.contains(this))
        {
            message.setFrom(devices.get(session.getId()));
            broadcast(message);
        }
    }
    
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException
    {
        deviceEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {

    }

    private static void broadcast(Message message) throws IOException, EncodeException
    {
        deviceEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                        .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

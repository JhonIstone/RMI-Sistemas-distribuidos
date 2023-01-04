import java.rmi.RemoteException;

public class app {

    public static void main(String[] args) {
        try {
            RmiServer server1 = new RmiServer(3232);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RmiClient client1 = new RmiClient("localhost", "3232");
        client1.RmiConect();
        client1.RmiSendMessage();
    }
}

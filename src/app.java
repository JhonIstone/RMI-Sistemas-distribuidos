import java.rmi.RemoteException;

public class app {
    public static void main(String[] args) {
        try {
            RmiServer server1 = new RmiServer(3232, 0, "sistemasdistribuidos");
            server1.addNewMember(new RmiServer(3333, 1, "sistemasdistribuidos2"));
            server1.addNewMember(new RmiServer(3434, 2, "sistemasdistribuidos3"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        RmiClient client1 = new RmiClient("localhost", "3232");
        client1.RmiConect();
        client1.RmiSendMessage();
    }
}
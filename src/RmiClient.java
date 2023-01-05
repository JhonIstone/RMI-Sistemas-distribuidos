import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RmiClient {
    private ReceiveMessageInterface rmiServer;
    private Registry registry;
    private String serverAddress;
    private String serverPort;

    public RmiClient(String serverAddress, String serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void RmiConect() {
        try {
            this.registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
            this.rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
        } catch (NumberFormatException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void RmiSendMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o comando SQL: ");
        String sql = scanner.nextLine();

        try {
            int status = this.rmiServer.receiveMessage(sql);
            System.out.println(status);
            // if (status == 200)
            // this.rmiServer.commitChanges();
            // else
            // System.out.println("Erro");
            // this.rmiServer.rollbackChanges();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

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
            this.rmiServer.receiveMessage(sql);
            // System.out.printf("Codigo Status: %d", status);
            // System.out.println();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // static public void main(String args[]) {
    // ReceiveMessageInterface rmiServer;
    // Registry registry;
    // String serverAddress = "localhost";
    // String serverPort = "3232";

    // while (true) {
    // // String sql = "CREATE TABLE users (id INTEGER PRIMARY KEY, name
    // VARCHAR(255),
    // // email VARCHAR(255))";
    // // String sql = "INSERT INTO users (id, name, email) VALUES (2, 'JOAO',
    // // 'JPCAR200@GMAIL.COM')" ;
    // Scanner scanner = new Scanner(System.in);
    // System.out.print("Informe o comando SQL: ");
    // String sql = scanner.nextLine();
    // try {
    // registry = LocateRegistry.getRegistry(serverAddress, (new
    // Integer(serverPort)).intValue());
    // rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
    // int status = rmiServer.receiveMessage(sql);
    // System.out.printf("Codigo Status: %d", status);
    // System.out.println();
    // } catch (RemoteException e) {
    // e.printStackTrace();
    // } catch (NotBoundException e) {
    // e.printStackTrace();
    // }
    // }
    // }
}

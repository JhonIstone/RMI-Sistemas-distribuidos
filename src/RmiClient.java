import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RmiClient {
    static public void main(String args[]) {
        ReceiveMessageInterface rmiServer;
        Registry registry;
        String serverAddress = "localhost";
        String serverPort = "3232";

        while (true) {
            // String sql = "CREATE TABLE users (id INTEGER PRIMARY KEY, name VARCHAR(255),
            // email VARCHAR(255))";
            // String sql = "INSERT INTO users (id, name, email) VALUES (2, 'JOAO',
            // 'JPCAR200@GMAIL.COM')" ;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Informe o comando SQL: ");
            String sql = scanner.nextLine();
            try {
                registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
                rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
                int status = rmiServer.receiveMessage(sql);
                System.out.printf("Codigo: %d", status);
                System.out.println();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }
}

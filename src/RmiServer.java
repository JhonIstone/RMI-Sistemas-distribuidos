import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.util.*;

public class RmiServer extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {
    int thisPort;
    String thisAddress;
    // List<Integer> array = new ArrayList<Integer>();

    Registry registry; // rmi registry for lookup the remote objects.

    // This method is called from the remote client by the RMI.
    // This is the implementation of the �ReceiveMessageInterface�.
    public int receiveMessage(String comandoSql) throws RemoteException {
        Conexao conecta = new Conexao();
        Connection con = conecta.Conexao();

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(comandoSql);
            con.close();
            return 200;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getErrorCode();
        }
    }

    public RmiServer(Integer porta) throws RemoteException {
        try {
            // get the address of this host.
            thisAddress = (InetAddress.getLocalHost()).toString();
        } catch (Exception e) {
            throw new RemoteException("can't get inet address.");
        }
        thisPort = porta; // this port(registry�s port)
        System.out.println("Conectado address=" + thisAddress + "- port=" + thisPort);

        try {
            // create the registry and bind the name and object.
            registry = LocateRegistry.createRegistry(thisPort);
            registry.rebind("rmiServer", this);
        } catch (RemoteException e) {
            throw e;
        }
    }

    static public void main(String args[]) {
        try {
            RmiServer server1 = new RmiServer(3232);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
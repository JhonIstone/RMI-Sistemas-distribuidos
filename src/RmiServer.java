import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.util.*;

public class RmiServer extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {
    private int id;
    private int thisPort;
    private String thisAddress;
    Registry registry;
    private String database;

    Conexao conecta = new Conexao();
    ArrayList<RmiServer> servers = new ArrayList<RmiServer>();

    public void receiveMessage(String comandoSql) throws RemoteException {
        for (RmiServer server : servers) {
            server.sendCommand(comandoSql);
        }
        this.sendCommand(comandoSql);
    }

    private int sendCommand(String comandoSql) {
        Connection con = conecta.Conexao("jdbc:sqlserver://localhost:1433", "joao.santos",
                "joao3257",
                database);

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(comandoSql);
            con.close();
            return 200;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }

    public RmiServer(Integer porta, int id, String dataBase) throws RemoteException {
        try {
            this.thisAddress = (InetAddress.getLocalHost()).toString();
            this.id = id;
            this.database = dataBase;
        } catch (Exception e) {
            throw new RemoteException("can't get inet address.");
        }
        this.thisPort = porta;
        System.out.println("Conectado address=" + this.thisAddress + "- port=" + this.thisPort);

        try {
            registry = LocateRegistry.createRegistry(thisPort);
            registry.rebind("rmiServer", this);
        } catch (RemoteException e) {
            throw e;
        }
    }

    public void addNewMember(RmiServer server) {
        servers.add(server);
    }
}
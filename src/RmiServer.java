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

    public int receiveMessage(String comandoSql) throws RemoteException {
        // servers.remove(1);

        // for (RmiServer server : servers) {
        for (int i = 0; i < servers.size(); i++) {
            int status;
            status = servers.get(i).sendCommand(comandoSql);
            if (status == 200)
                continue;
            else {
                System.out.printf("Erro na maquina: %d", servers.get(i).id);
                System.out.println();
                servers.remove(i);
            }
        }

        int status = this.sendCommand(comandoSql);
        if (status != 200) {
            System.out.println("Erro maquina principal");
            System.out.println();
        }
        // for (RmiServer server : servers) {
        for (int i = 0; i < servers.size(); i++) {
            System.out.printf("Maquina: %d", servers.get(i).id);
            System.out.println();
        }
        return 200;
    }

    private int sendCommand(String comandoSql) {
        Connection con = conecta.Conexao("jdbc:sqlserver://localhost:1433", "joao.santos", "joao3257", database);
        Statement stmt;
        try {
            // con.setAutoCommit(false);
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
        this.servers.add(server);
    }
}
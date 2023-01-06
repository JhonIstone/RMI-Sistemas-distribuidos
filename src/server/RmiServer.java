package server;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.util.*;

import interfaces.ReceiveMessageInterface;

public class RmiServer extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {
    private int id;
    private int thisPort;
    private String thisAddress;
    Registry registry;
    private String database;
    Conexao conecta = new Conexao();
    ArrayList<RmiServer> servers = new ArrayList<RmiServer>();

    public int receiveMessage(String comandoSql) throws RemoteException {
        int status = this.sendCommand(comandoSql); // 208
        if (status != 200) {
            System.out.println("Erro maquina principal");
            System.out.println();
            return status;
        }

        for (int i = 0; i < servers.size(); i++) {
            status = servers.get(i).sendCommand(comandoSql);
            if (status == 200)
                continue;
            else {
                System.out.printf("Erro na maquina: %d", servers.get(i).id);
                System.out.println();
                servers.remove(i);
            }
        }

        // for (RmiServer server : servers) {
        for (int i = 0; i < servers.size(); i++) {
            System.out.printf("Maquina: %d", servers.get(i).id);
            System.out.println();
        }
        return 200;
    }

    private int sendCommand(String comandoSql) {
        try (Connection con = conecta.Conexao("jdbc:sqlserver://localhost:1433", "joao.santos", "joao3257", database)) {
            Statement stmt;
            try {
                // con.setAutoCommit(false);
                stmt = con.createStatement();
                stmt.executeUpdate(comandoSql);
                con.close();
                return 200;
            } catch (SQLException e) {
                System.out.printf("erro na maquina %d", this.id);
                System.out.println();
                return e.getErrorCode();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            return 400;
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

    public ArrayList<RmiServer> getServers() {
        return servers;
    }

    public int getId() {
        return id;
    }

    public int getThisPort() {
        return thisPort;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBiggerGroup() {
        RmiServer server = null;

        for (int i = 0; i < servers.size(); i++) {
            if (server == null) {
                server = servers.get(i);
            } else if (servers.get(i).id > server.id) {
                server = servers.get(i);
            }
        }
        this.id = 0;
        return server.thisPort;
    }

}
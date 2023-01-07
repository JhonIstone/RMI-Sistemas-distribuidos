package client;

import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.ReceiveMessageInterface;
import server.RmiServer;

public class RmiClient {
    private ReceiveMessageInterface rmiServer;
    private Registry registry;
    private String serverAddress;
    private int serverPort;

    public RmiClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void RmiConect() {
        try {
            this.registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
            this.rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
        } catch (NumberFormatException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("Erro ao conectar client");
        }
    }

    private void RmiSendMessage(String sql) {
        try {
            int status = this.rmiServer.receiveMessage(sql);
            if (status == 200) {
                System.out.println("Comando executado");
            } else if (status == 2627) {
                System.out.println("Comando invalido");
            } else if (status == 400) {
                System.out.println("Trocando de server");

                int newPorta = this.rmiServer.getBiggerGroup();
                this.serverPort = newPorta;
                this.RmiConect();
                this.RmiSendMessage(sql);
            }
        } catch (RemoteException e) {
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
            if (status == 200) {
                System.out.println("Comando executado");
            } else if (status == 2627) {
                System.out.println("Comando invalido");
            } else if (status == 400) {
                System.out.println("Trocando de server");

                int newPorta = this.rmiServer.getBiggerGroup();

                this.serverPort = newPorta;
                this.RmiConect();
                this.RmiSendMessage(sql);
                // }
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

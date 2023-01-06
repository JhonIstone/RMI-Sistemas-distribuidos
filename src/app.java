import java.rmi.RemoteException;
import java.util.Scanner;

import client.RmiClient;
import server.RmiServer;

public class app {
    public static void main(String[] args) {
        try {
            RmiServer server1 = new RmiServer(3232, 0, "sistemasdistribuidos");
            server1.addNewMember(new RmiServer(3333, 1, "sistemasdistribuidos2"));
            server1.addNewMember(new RmiServer(3434, 2, "sistemasdistribuidos3"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        RmiClient client1 = new RmiClient("localhost", 3232);
        client1.RmiConect();

        int condicion = -1;
        while (condicion != 0) {
            System.out.println("----------- BEM VINDO AO SISTEMA -----------");
            System.out.println("1- Executar comando SQL");
            System.out.println("0- Sair");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Informe a opção: ");
            condicion = scanner.nextInt();

            if (condicion == 1) {
                client1.RmiSendMessage();
            } else
                continue;
        }
    }
}
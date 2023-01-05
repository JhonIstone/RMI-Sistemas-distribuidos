import java.rmi.*;

public interface ReceiveMessageInterface extends Remote {
	int receiveMessage(String comandoSql) throws RemoteException;

	// void commitChanges() throws RemoteException;

	// void rollbackChanges() throws RemoteException;
}

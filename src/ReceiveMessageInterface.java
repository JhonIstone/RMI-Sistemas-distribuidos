import java.rmi.*;

public interface ReceiveMessageInterface extends Remote {
	void receiveMessage(String comandoSql) throws RemoteException;
}

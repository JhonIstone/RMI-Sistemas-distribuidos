import java.rmi.*;

public interface ReceiveMessageInterface extends Remote {
	int receiveMessage(String x) throws RemoteException;
}

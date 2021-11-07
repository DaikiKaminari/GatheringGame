package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Position extends Remote {
	double getX() throws RemoteException;
	double getY() throws RemoteException;
	void setX(double x) throws RemoteException;
	void setY(double y) throws RemoteException;
	void moveX(double deltaX) throws RemoteException;
	void moveY(double deltaY) throws RemoteException;
	Position clonePos() throws RemoteException;


	static boolean isOverlapping(Position p1, Position p2, double radius) throws RemoteException {
		return (p1.getX() - radius <= p2.getX()
				&& p1.getX() + radius >= p2.getX()
				&& p1.getY() - radius <= p2.getY()
				&& p1.getY() + radius >= p2.getY());
	}
}

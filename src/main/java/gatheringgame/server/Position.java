package gatheringgame.server;

import gatheringgame.server.impl.PositionImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

public interface Position extends Remote {
	double getX() throws RemoteException;
	double getY() throws RemoteException;
	void setX(double x) throws RemoteException;
	void setY(double y) throws RemoteException;
	void moveX(double deltaX) throws RemoteException;
	void moveY(double deltaY) throws RemoteException;
	Position clonePos() throws RemoteException;

	private static boolean isPointInnerRectangle(Position point, Position minRect, Position maxRect) throws RemoteException {
		return point.getX() >= minRect.getX()
				&& point.getX() <= maxRect.getX()
				&& point.getY() >= minRect.getY()
				&& point.getY() <= maxRect.getY();
	}


	static boolean isOverlapping(Position player, Position resource, int sizePlayer, int sizeObject) throws RemoteException {
		Position playerMax = new PositionImpl(player.getX() + sizePlayer, player.getX() + sizePlayer);
		List<Position> resourcePoints = Arrays.asList(
				resource,
				new PositionImpl(resource.getX() + sizeObject, resource.getY()),
				new PositionImpl(resource.getX(), resource.getY() + sizeObject),
				new PositionImpl(resource.getX() + sizeObject, resource.getY() + sizeObject)
		);
		int pointsInner = 0;
		int pointsOutside = 0;
		for (Position point : resourcePoints) {
			if (isPointInnerRectangle(point, player, playerMax)) {
				pointsInner++;
			} else {
				pointsOutside++;
			}
		}
		return pointsInner > 0 && pointsOutside > 0;
	}
}

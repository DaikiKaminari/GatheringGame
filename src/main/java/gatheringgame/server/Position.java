package gatheringgame.server;

import gatheringgame.server.impl.PositionImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

public interface Position extends Remote {
    private static boolean isPointInnerRectangle(Position point, Position minRect, Position maxRect) throws RemoteException {
        return point.getX() >= minRect.getX()
                && point.getX() <= maxRect.getX()
                && point.getY() >= minRect.getY()
                && point.getY() <= maxRect.getY();
    }

    static boolean isOverlapping(Position player, Position resource, int sizePlayer, int sizeObject) throws RemoteException {
        Position playerMax = new PositionImpl(player.getX() + sizePlayer, player.getY() + sizePlayer);
        List<Position> resourcePoints = Arrays.asList(
                resource,
                new PositionImpl(resource.getX() + sizeObject, resource.getY()),
                new PositionImpl(resource.getX(), resource.getY() + sizeObject),
                new PositionImpl(resource.getX() + sizeObject, resource.getY() + sizeObject)
        );
        for (Position point : resourcePoints) {
            if (isPointInnerRectangle(point, player, playerMax)) {
                return true;
            }
        }
        Position objectMax = new PositionImpl(resource.getX() + sizeObject, resource.getY() + sizeObject);
        List<Position> playerPoints = Arrays.asList(
                player,
                new PositionImpl(player.getX() + sizePlayer, player.getY()),
                new PositionImpl(player.getX(), player.getY() + sizePlayer),
                new PositionImpl(player.getX() + sizePlayer, player.getY() + sizePlayer)
        );
        for (Position point : playerPoints) {
            if (isPointInnerRectangle(point, resource, objectMax)) {
                return true;
            }
        }
        return false;
    }

    double getX() throws RemoteException;

    void setX(double x) throws RemoteException;

    double getY() throws RemoteException;

    void setY(double y) throws RemoteException;

    void moveX(double deltaX) throws RemoteException;

    void moveY(double deltaY) throws RemoteException;

    Position clonePos() throws RemoteException;
}

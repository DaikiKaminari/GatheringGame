package gatheringgame.server.impl;

import gatheringgame.server.Position;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PositionImpl extends UnicastRemoteObject implements Position {
	double x;
	double y;

	public PositionImpl(double x, double y) throws RemoteException{
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() throws RemoteException {
		return x;
	}

	@Override
	public double getY() throws RemoteException {
		return y;
	}

	@Override
	public void setX(double x) throws RemoteException {
		this.x = x;
	}

	@Override
	public void setY(double y) throws RemoteException {
		this.y = y;
	}

	@Override
	public void moveX(double deltaX) throws RemoteException {
		x += deltaX;
	}

	@Override
	public void moveY(double deltaY) throws RemoteException {
		y += deltaY;
	}

	@Override
	public Position clonePos() throws RemoteException {
		return new PositionImpl(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PositionImpl position = (PositionImpl) o;
		return (x == position.x && y == position.y);
	}

}

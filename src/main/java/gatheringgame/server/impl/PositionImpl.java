package gatheringgame.server.impl;

import gatheringgame.server.Position;

import java.rmi.RemoteException;

public class PositionImpl implements Position {
	int x;
	int y;

	public PositionImpl(int x, int y) throws RemoteException{
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() throws RemoteException {
		return x;
	}

	@Override
	public int getY() throws RemoteException {
		return y;
	}

	@Override
	public void setX(int x) throws RemoteException {
		this.x = x;
	}

	@Override
	public void setY(int y) throws RemoteException {
		this.y = y;
	}
}

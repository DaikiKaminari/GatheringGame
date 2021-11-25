package gatheringgame.server.impl;

import gatheringgame.server.Position;
import gatheringgame.server.Resource;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ResourceImpl extends UnicastRemoteObject implements Resource {
	private final Item item;
	private final Position position;

	public ResourceImpl(Item item, Position position) throws RemoteException {
		super();
		this.item = item;
		this.position = position;
	}

	@Override
	public String getName() throws RemoteException {
		return item.getName();
	}

	@Override
	public Position getPos() throws RemoteException {
		return position;
	}

	@Override
	public Item getItem() {
		return this.item;
	}
}

package gatheringgame.server.impl;

import gatheringgame.server.Position;

public class PositionImpl implements Position {
	int x;
	int y;

	public PositionImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

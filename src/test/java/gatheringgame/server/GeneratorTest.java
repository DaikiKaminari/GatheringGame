package gatheringgame.server;

import gatheringgame.server.impl.GeneratorImpl;
import gatheringgame.server.impl.PositionImpl;
import gatheringgame.server.util.Rand;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.mockito.Mockito.*;

public class GeneratorTest {
	@Test
	public void randomResourceTest() throws RemoteException {
		Position p1 = new PositionImpl(2, 3);
		Position p2 = new PositionImpl(6, 7);
		Rand randStub = mock(Rand.class);
		when(randStub.randomInt(anyInt(), anyInt())).thenReturn(5, 4);
		GeneratorImpl gen = new GeneratorImpl(p1, p2, randStub);
		Resource r = gen.randomResource();
	}
}

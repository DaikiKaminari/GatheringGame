package gatheringgame.server;

import gatheringgame.server.impl.Item;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public interface Resource extends Remote {
	String getName() throws RemoteException;
	Position getPos() throws RemoteException;
	Item getItem() throws RemoteException;

	static Item leastOccurrenceItem(List<Resource> resources) throws RemoteException {
		if (resources.isEmpty()) return Item.randomItem();

		Map<Item, Integer> occurrences = new EnumMap<>(Item.class);
		for(Item i : Item.values()) {
			occurrences.put(i, 0);
		}

		try {
			for (Resource resource : resources) {
				Item it = resource.getItem();
				int count = occurrences.get(it) + 1;
				occurrences.put(it, count);
			}
		} catch (RemoteException e) {
			// N'est pas censé arriver car c'est une méthode exécutée uniquement côté serveur
			e.printStackTrace();
		}

		Map.Entry<Item, Integer> minOccurence = Collections.min(occurrences.entrySet(),
				Map.Entry.comparingByValue());
		return minOccurence.getKey();
	}
}

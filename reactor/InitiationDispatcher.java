import java.nio.channels.*;
import java.util.*;

class InitiationDispatcher {
	private static InitiationDispatcher instance;
	Map<Handle, EventHandler> handleMap = new HashMap<>();
	Map<Handle, Acceptor> acceptorMap = new HashMap<>();

	static synchronized InitiationDispatcher getInstance() {
		if (instance == null) {
			instance =  new InitiationDispatcher();
		}
		return instance;
	}

	SelectionKey registerAcceptor(Acceptor a) {
		Handle h = a.getHandle();
		acceptorMap.put(h, a);
		return SynchronousEventDemux.getInstance().register(h);
	}

	SelectionKey register(EventHandler e) {
		Handle h = e.getHandle();
		handleMap.put(h, e);
		return SynchronousEventDemux.getInstance().register(h);
	}
	
	void handleEvents() {
		while (true) {
			Set<Handle> selectedHandles = SynchronousEventDemux.getInstance().select();
			for (Handle h : selectedHandles) {
				EventHandler e = handleMap.get(h);
				if (e != null) {
					e.handleEvent();
				} else {
					Acceptor a = acceptorMap.get(h);
					a.handleConnect();
				}
			}
		}
	}
}

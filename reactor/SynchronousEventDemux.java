import java.nio.channels.*;
import java.util.*;

class SynchronousEventDemux {
	private static SynchronousEventDemux instance;
	private Selector selector;
	private Map<Channel, Handle> channelHandleMap = new HashMap<>();
	private SynchronousEventDemux() {
		try {
			selector = Selector.open();
		} catch (Exception e) {
			/* do nothing for now. */
			e.printStackTrace();
		}
	}

	synchronized static SynchronousEventDemux getInstance() {
		if (instance == null) {
			instance = new SynchronousEventDemux();
		}

		return instance;
	}

	SelectionKey register(Handle h) {
		try {
			SelectableChannel sc = h.getChannel();
			sc.register(selector, h.getSocketOperation());
			channelHandleMap.put(sc, h);

			return sc.keyFor(selector);
		} catch (Exception e) {
			/* do nothing for now. */
			e.printStackTrace();
		}

		return null;
	}

	 Set<Handle> select() {
		Set<Handle> handles = new HashSet<>();

		try {
			selector.select();
			for (Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); iter.hasNext(); ) {
				handles.add(channelHandleMap.get(iter.next().channel()));
				iter.remove();
			}
		} catch (Exception e) {
			/* do nothing for now. */
			e.printStackTrace();
		}

		return handles;
	}
}

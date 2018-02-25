import java.net.*;
import java.nio.channels.*;
import java.nio.*;

class EventHandler {
	SocketChannel socketChannel;
	SelectionKey sKey;
	Handle handle;

	EventHandler(SocketChannel socketChannel) {
		try {
			this.socketChannel = socketChannel;
    	    socketChannel.configureBlocking(false);
        	handle = new Handle(socketChannel, SelectionKey.OP_READ);
			sKey = InitiationDispatcher.getInstance().register(this);
		} catch (Exception e) {
			/* Do nothing for now. */
			e.printStackTrace();
		}
	}

    void print(SelectionKey selectionKey) {
        System.out.println("EventHandler :" + selectionKey.isAcceptable() + ":" + selectionKey.isConnectable() + ":" + selectionKey.isReadable() + ":" + selectionKey.isWritable());
    }

	void handleEvent() {
		try {
			if (sKey.isReadable()) {
				// you can read in multiple iterations.
				int someBytes = 1;
				int bytesRead = socketChannel.read(ByteBuffer.allocate(someBytes));
				// recommended way to handle client disconnects.
				if (bytesRead < 0) {
					socketChannel.close();
				} else {
					System.out.println("Got read event and read " + bytesRead + " bytes");
				}
			}
		} catch (Exception e) {
			/* Do nothing for now. */
			e.printStackTrace();
		}
	}

	Handle getHandle() {
		return handle;
	}
}

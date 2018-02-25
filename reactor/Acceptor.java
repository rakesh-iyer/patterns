import java.net.*;
import java.nio.channels.*;

class Acceptor {
	ServerSocketChannel socketChannel;
	SelectionKey sKey;
	Handle handle;

	Acceptor() {
		try {
            socketChannel = ServerSocketChannel.open();
			socketChannel.bind(new InetSocketAddress("localhost", 1234));
			socketChannel.configureBlocking(false);
			handle = new Handle(socketChannel, SelectionKey.OP_ACCEPT);
    		sKey = InitiationDispatcher.getInstance().registerAcceptor(this);
		} catch (Exception e) {
			/* */
			e.printStackTrace();
		}
	}

	Handle getHandle() {
		return handle;
	}

	void handleConnect() {
		try {
			if (!sKey.isAcceptable()) {
				return;
			}

			System.out.println("Got new connection");
			SocketChannel sc = socketChannel.accept();
			if (sc == null) {
				return;
			}

			EventHandler e = new EventHandler(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

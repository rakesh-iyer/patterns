import java.net.*;
import java.nio.channels.*;;

class Handle {
	SelectableChannel channel;
	int socketOps;

	Handle(SelectableChannel channel, int socketOps) {
		this.channel = channel;
		this.socketOps = socketOps;
	}

	SelectableChannel getChannel() {
		return channel;
	}

	int getSocketOperation() {
		return socketOps;
	}
}

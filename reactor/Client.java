import java.nio.channels.*;
import java.net.*;
import java.nio.*;

class Client {
    public static void main(String args[]) throws Throwable {
        int n = 1;

        for (int i = 0; i < n; i++) {
            SocketChannel sc = SocketChannel.open();
            sc.bind(null);
            sc.connect(new InetSocketAddress("localhost", 1234));

			byte ba[] = {1,2,3,4};
			ByteBuffer bb = ByteBuffer.wrap(ba);
			
			sc.write(bb);
        }
    }
}

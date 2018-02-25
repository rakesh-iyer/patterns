class Server {
	public static void main(String args[]) {
		Acceptor a = new Acceptor();

    	InitiationDispatcher.getInstance().handleEvents();
	}
}

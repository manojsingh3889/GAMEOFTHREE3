package com.takeaway.player.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ListeningServerSocket {
	private static ServerSocket serverSocket;
	private ListeningServerSocket(){}

	public static void init(int listeningPort) throws UnknownHostException, IOException{
		//since Player module is a single threaded so we don't need synchronization here
		if(serverSocket == null){
			serverSocket = new ServerSocket(listeningPort,50,InetAddress.getLocalHost());
		}
	}

	public static int port(){
		return serverSocket.getLocalPort();
	}

	public static String host(){
		return serverSocket.getInetAddress().getHostAddress();
	}

	public static void shutdown(){
		try {
			System.out.println("Shutding down listening socket");
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startSocket() throws IOException{
		System.out.println("\n\n********************************************************************");
		GameServer session = new GameServer();
		session.start(serverSocket);
		System.out.println("********************************************************************");
	} 
	
	@Override
	protected void finalize() throws Throwable {
		shutdown();
	}
}

package com.takeaway.player;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import static com.takeaway.player.core.Const.*;
import com.takeaway.player.core.ListeningServerSocket;
import com.takeaway.player.core.PlayerGameSocketClient;

public class PlayerConsole{
	protected int listeningPort;
	public static String gameType;
	public static String userName;
	public static Scanner scan = new Scanner(System.in);
	
	public PlayerConsole(int listeningPort){
		this.listeningPort = listeningPort;
	}

	public void init(){
		try {
			ListeningServerSocket.init(listeningPort);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(SOCKET_ERROR);
			System.exit(1);
		}
		
		System.out.println(WELCOME_NOTE);
		
		System.out.print(ENTER_NAME);
		String uname = scan.nextLine();
		userName = uname.trim()+"_"+listeningPort;
		
		System.out.print(ENTER_GAME_TYPE);
		String gtype = scan.nextLine();
		gtype = gtype.trim();
		if("M".equalsIgnoreCase(gtype) || GAME_MANUAL.equalsIgnoreCase(gtype)){
			gameType=GAME_MANUAL;
		}else if("A".equalsIgnoreCase(gtype) || GAME_AUTO.equalsIgnoreCase(gtype)){
			gameType=GAME_AUTO;
		}else{
			System.out.println(EXIT_INVALID_INPUT);
			System.exit(1);
		}
		
		System.out.print(ENTER_COMMAND);
		String step = PlayerConsole.scan.nextLine();
		if("J".equalsIgnoreCase(step) || JOIN.equalsIgnoreCase(step)){
			joinGame();
		}else if("C".equalsIgnoreCase(step) || CREATE.equalsIgnoreCase(step)){
			startNewGame();
		}else{
			System.out.println(EXIT_INVALID_INPUT);
		}
	}

	public void joinGame(){
		System.out.println(ENTER_OPPONENT_DETAIL);
		System.out.print(OPPONENT_IP);
		String hostname = PlayerConsole.scan.nextLine();
		System.out.print(OPPONENT_PORT);
		int port = PlayerConsole.scan.nextInt();
		PlayerConsole.scan.nextLine();
		System.out.print(OPPONENT_NAME);
		String opponentName = PlayerConsole.scan.nextLine();
		System.out.println();
		PlayerGameSocketClient client = new PlayerGameSocketClient(
				hostname, port,opponentName);
		client.play();
	}

	public void startNewGame(){
		try {
			System.out.println("Your game details \n Server IP : "+InetAddress.getLocalHost()+""
					+ "\n Server Port : "+this.listeningPort);
			ListeningServerSocket.startSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
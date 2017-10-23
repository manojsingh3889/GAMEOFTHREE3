package com.takeaway.player;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

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
			System.out.println("Unable to establish server socket.\nExiting game");
			System.exit(1);
		}
		
		System.out.println("************Welcome to GAME_OF_THREE************\n\nCompete to become the Ruler of realm >>THREE<<\n");
		
		System.out.print("Please enter your display name:");
		String uname = scan.nextLine();
		userName = uname.trim()+"_"+listeningPort;
		
		System.out.print("Please select game type [(A)uto , (M)anual]:");
		String gtype = scan.nextLine();
		gtype = gtype.trim();
		if("M".equalsIgnoreCase(gtype) || "Manual".equalsIgnoreCase(gtype)){
			gameType="MANUAL";
		}else if("A".equalsIgnoreCase(gtype) || "Auto".equalsIgnoreCase(gtype)){
			gameType="AUTO";
		}else{
			System.out.println("Invalid input. Exiting program.");
			System.exit(1);
		}
		
		System.out.print("Please enter (J)oin or (C)reate game :");
		String step = PlayerConsole.scan.nextLine();
		if("J".equalsIgnoreCase(step) || "Join".equalsIgnoreCase(step)){
			joinGame();
		}else if("C".equalsIgnoreCase(step) || "Create".equalsIgnoreCase(step)){
			startNewGame();
		}else{
			System.out.println("Invalid input. Game Exiting.");
		}
	}

	public void joinGame(){
		System.out.println("Please enter opponent details.");
		System.out.print("Opponent IP:");
		String hostname = PlayerConsole.scan.nextLine();
		System.out.print("Opponent port:");
		int port = PlayerConsole.scan.nextInt();
		PlayerConsole.scan.nextLine();
		System.out.print("Opponent name:");
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
package com.takeaway.player.core;

import com.takeaway.player.PlayerConsole;

public class PlayerGameSocketClient {
	private String playerName;
	private String serverIP;
	private int serverPort;

	public PlayerGameSocketClient(String serverIP,int serverPort,String playerName){
		this.playerName = playerName;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public void play(){
		System.out.println("********************************************************************");
		GameClient session = new GameClient();
		session.start(false,this.playerName,PlayerConsole.userName,serverIP,serverPort);
		System.out.println("********************************************************************");
	}
}

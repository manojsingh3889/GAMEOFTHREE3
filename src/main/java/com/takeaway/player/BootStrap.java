package com.takeaway.player;

public class BootStrap {
	public static void main(String[] args) {
		
		if(args.length>0){
			int listeningPort = Integer.parseInt(args[0]);
				PlayerConsole console = new PlayerConsole(listeningPort);
				console.init();
			}else{
				System.out.println("usage: java -jar Player.jar <listening-port>");
			}
		
		
	}
}

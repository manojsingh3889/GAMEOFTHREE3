package com.takeaway.player.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class GameClient {
	public void start(boolean owner,String opponent,String you,String ip,int port) {
		Socket sock = null;
		//		BufferedReader keyRead = null;
		OutputStream ostream = null;
		PrintWriter pwrite = null;
		InputStream istream = null;
		BufferedReader receiveRead =null;
		boolean isStarted = true;
		try {
			sock = new Socket(ip, port);
			// sending to client (pwrite object)
			ostream = sock.getOutputStream(); 
			pwrite = new PrintWriter(ostream, true);

			// receiving from server ( receiveRead  object)
			istream = sock.getInputStream();
			receiveRead = new BufferedReader(new InputStreamReader(istream));

			Game game = new Game();

			String receiveMessage;               
			while(true)
			{	
				if(isStarted){
					//ask to make first move
					pwrite.println("Hi "+opponent+". I am "+you+". Please start the game...");
					pwrite.flush();                    // flush the data
					isStarted = false;
					//print game start message
					System.out.println("\n\n\tGame Started.. Wait for player response..");
				}else{
					//read first move from owner
					if((receiveMessage = receiveRead.readLine()) != null){	
						//receive from server
						//make next move
						if(!game.nextMove(receiveMessage, pwrite)) break;
					}     
				}
			} 
		}catch(SocketException e){
			System.out.println("Player left.");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				receiveRead.close();
				istream.close();
				pwrite.close();
				ostream.close();
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
}

package com.takeaway.player.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class GameServer {

	//	private Socket s;

	public GameServer() {

	}

	public void start(ServerSocket sersock) {
		Socket sock = null;
		OutputStream ostream = null;
		PrintWriter pwrite = null;
		InputStream istream = null;
		BufferedReader receiveRead = null;
		boolean isStarted = true;
		try {
			System.out.println("Waiting for other user");
			sock = sersock.accept( );
			
			/** Create resources**/
			ostream = sock.getOutputStream(); 
			pwrite = new PrintWriter(ostream, true);
			// receiving from server ( receiveRead  object)
			istream = sock.getInputStream();
			receiveRead = new BufferedReader(new InputStreamReader(istream));
			Game game = new Game();
			/**Resources created**/

			String receiveMessage;               
			while(true)
			{
				if (isStarted) {
					if((receiveMessage = receiveRead.readLine()) != null){  
						System.out.println("\n\tPlayer: "+receiveMessage);         
						//make first move
						game.firstMove(pwrite);
						isStarted = false;
					}
				}else{
					if((receiveMessage = receiveRead.readLine()) != null){  
						if(!game.nextMove(receiveMessage, pwrite))
							break;
					}
				}
			}               

		} catch(SocketException e){
			System.out.println("\tGame terminated by opponent.");
		}catch (IOException e) {
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

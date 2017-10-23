package com.takeaway.player.core;

import java.io.IOException;
import java.io.PrintWriter;

import com.takeaway.player.PlayerConsole;

public class Game {

	public void firstMove(PrintWriter write) throws IOException{
		System.out.print("\tStart by entering random number :");
		int first = PlayerConsole.scan.nextInt();
		System.out.println("\tYou Started");
		System.out.println("\tYou:[First move =" + first + "]");
		//-2 to indicate that its a first move
		write.println(new StringBuilder().append(-2).append(":").append(first).toString());             
		write.flush();
	}

	public boolean nextMove(String counterMove, PrintWriter write){

		int playerAdded = Integer.parseInt(counterMove.split(":")[0]);
		int result = Integer.parseInt(counterMove.split(":")[1]);
		
		if (playerAdded == -2) {
			System.out.println("\tPlayer: [First move =" + result + "]");
		}else{
			System.out.println("\tPlayer: [added=" + playerAdded + " , resultant=" + result + "]");
		}
		
		if(result == 1){
			System.out.println("\tPlayer won the game... Better luck next time");
			return false;
		}
		
		int yourMove = "AUTO".equalsIgnoreCase(PlayerConsole.gameType)? calculateMove(result):enterMove(result);
		
		int youAdded = (yourMove*3)-result;
		String yourResponse = new StringBuilder().append(youAdded).append(":").append(yourMove).toString();
		System.out.println("\tYou: [added=" + youAdded + " , resultant=" + yourMove + "]");
		write.println(yourResponse);             
		write.flush();
		
		if(yourMove == 1){
			System.out.println("\n\tCongratulations!!...You won the game.");
			return false;
		}
	
		return true;
	}
	
	public Integer calculateMove(Integer input){
		try {Thread.sleep(1000);}catch (InterruptedException e) {}

		int remainder = input % 3;
		if(remainder == 0){
			return input/3;
		}else if(remainder == 1){
			return (input-1)/3;
		}else{
			return (input+1)/3;
		}
	}
	
	public Integer enterMove(Integer input){
		while(true){
			System.out.print("\tPlease add number (-1,0,1) only :");
			int add = PlayerConsole.scan.nextInt();
			if(add == -1 || add == 0 || add == 1){
				if((input+add)%3==0){
					return ((input+add)/3);
				}else{
					System.out.println("\t"+input+" + "+add+"="+(input+add)+" is not divisible by 3. Try again");
				}
			}else{
				System.out.println("\tWrong input. Try again");
			}
		}
	}
}

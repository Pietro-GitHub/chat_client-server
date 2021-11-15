package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads){
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run(){
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);
            while(true){
                String outputString = input.readLine();
                if(outputString.equals("exit")){
                    break;
                }
                printToALlClients(outputString);
                System.out.println("Il server ha ricevuto " + outputString);
            }
        } catch (Exception e) {
            System.out.println("Si e' verificato un errore " + e.getStackTrace());
        }
    }

    private void printToALlClients(String outputString){
        for(ServerThread sT: threadList){
            sT.output.println(outputString);
        }
    }
}
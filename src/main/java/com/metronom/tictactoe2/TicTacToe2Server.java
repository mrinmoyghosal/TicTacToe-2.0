package com.metronom.tictactoe2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.metronom.tictactoe2.engine.PlayEngine;


public class TicTacToe2Server {
	

    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(58901)) {
            System.out.println("Tic Tac Toe Server is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(200);
            
            TicTacToe2 tictactoe = new TicTacToe2();
            
            boolean stat = true;
            System.out.println("Loop Started");
            while (stat) {
                Socket conn = listener.accept();
                InputStream in = conn.getInputStream();
                int data = in.read();
                System.out.println(data);
                OutputStream out = conn.getOutputStream();
                out.write(10);
            }
        }
    }
	


}

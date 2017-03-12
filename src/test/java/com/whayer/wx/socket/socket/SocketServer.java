package com.whayer.wx.socket.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {

	public static void main(String[] args) {
		try {
			System.out.println("-----启动socket服务------");
			ServerSocket serversocket = new ServerSocket(1346);
			Socket socket = null;
			while (true) {
				socket = serversocket.accept();
				System.out.println("欢迎您..");
				SocketThread thread = new SocketThread(socket);
				thread.start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

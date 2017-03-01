package com.whayer.wx.common.ssh;

import java.util.concurrent.LinkedBlockingQueue;


public class Executer extends Shell implements Runnable {
	private String[] commands;

	public Executer(String address, String user, String pwd, String id, LinkedBlockingQueue<String> queue) {
		super(address, user, pwd, id, queue);
	}

	public void addCommands(String[] commands) {
		this.commands = commands;
	}

	public void run() {
		for (String cmd : commands) {
			super.execute(cmd);
			while (!isValid) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		close();

	}

	public void close() {
		super.close();
	}

}

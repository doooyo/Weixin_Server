package com.whayer.wx.common.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class Shell {
	private static Logger log = LoggerFactory.getLogger(SSH.class);
	private String address;
	private String user;
	private String pwd;
	private String id;
	private Connection connection;
	private LinkedBlockingQueue<String> queue;
	private Session session;
	private long timeSession, timeCommand;
	protected boolean isValid = true;

	/**
	 * @param address
	 * @param user
	 * @param pwd
	 * @param queue
	 */
	public Shell(String address, String user, String pwd, String id, LinkedBlockingQueue<String> queue) {
		this.address = address;
		this.user = user;
		this.pwd = pwd;
		this.queue = queue;
		this.id = id;
		this.connection();
	}

	/**
	 * 创建SSH 连接
	 */
	public void connection() {
		timeSession = System.currentTimeMillis();
		connection = new Connection(address);
		try {
			connection.connect();
			if (connection.authenticateWithPassword(user, pwd) == false)
				throw new IOException("Authentication failed.");
		} catch (Exception e) {
			output(e.getMessage());
		}
	}

	/**
	 * execute a command
	 * 
	 * @param command
	 */
	public void execute(String command) {
		if (isValid) {
			isValid = false;
			try {
				session = connection.openSession();
				timeCommand = System.currentTimeMillis();
				output("<" + command + ">");
				session.execCommand(command);

				BufferedReader br = new BufferedReader(new InputStreamReader(session.getStdout()));
				String line;
				while ((line = br.readLine()) != null) {
					output(line);
				}
				timeCommand = System.currentTimeMillis() - timeCommand;
				output("Command Cost " + timeFormat(timeCommand) + "\n");
			} catch (IOException e) {
				output(e.getMessage());
				log.error(e.getMessage());
			} finally {
				session.close();
				isValid = true;
			}
		}
	}

	/**
	 * Close Session
	 */
	public void close() {
		timeSession = System.currentTimeMillis() - timeSession;
		output("Connection Closing " + timeFormat(timeSession) + "\n");
		/* Close the connection */
		session.close();
		connection.close();
	}

	private void output(String content) {
		try {
			queue.put(this.id + " :  " + content);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	private String timeFormat(long time) {
		long i, k = 0;
		StringBuilder sb = new StringBuilder();
		i = time / (1000 * 60);
		sb.append(i).append("m");
		i = time % (1000 * 60);
		k = i / 1000;
		sb.append(k).append("s");
		k = i % 1000;
		sb.append(k).append("ms");
		return sb.toString();
	}
}

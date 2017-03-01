package com.whayer.wx.common.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whayer.wx.common.X;

public final class Terminal {
	private static Logger log = LoggerFactory.getLogger(Terminal.class);
	private static final String APPEND_ERROR = " 2>&1 ";

	public static void execute() {
		ProcessBuilder pb = new ProcessBuilder();
		pb.command("vi", "1.txt");
		try {
			final Process p = pb.start();
			X.sleep(2);
			p.destroy();
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Execute a shell command
	 * 
	 * @param command
	 *            e.g. new String("ls")
	 * @param syn
	 *            ( block current thread to wait external process)
	 * @param workSpace
	 *            e.g. new String("/home/duyu")
	 * @return
	 */
	public static String[] execute(String command, String workSpace, boolean syn) {
		log.debug("Terminal.execute()");
		StringBuilder sb = new StringBuilder();
		String[] result = new String[2];
		BufferedReader bro;
		Process process = null;
		try {
			// 2>&1 将标准错误重定向到标准输出 避免 错误流塞满缓冲区 导致 外部进程阻塞.
			String[] cmd = new String[] { "/bin/bash", "-c", command + APPEND_ERROR };
			if (null != workSpace) {
				log.info("EXECUTE (3arguments) : {} > {}", workSpace, command);
				process = Runtime.getRuntime().exec(cmd, null, new File(workSpace));
			} else {
				log.info("EXECUTE (2arguments) : {}", command);
				process = Runtime.getRuntime().exec(cmd, null);
			}
			bro = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while ((s = bro.readLine()) != null) {
				log.info("Process stdout : {}", s);
				sb.append(s);
			}
			if (syn) {
				process.waitFor();
			}
			if (0 == process.exitValue()) {
				result[0] = X.SUCCESS;
			} else {
				result[0] = X.FAIL;
				log.error("Process failed : \n" + result[1]);
			}
			result[1] = sb.toString();
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param command
	 *            e.g. new String("tail -f xxxx")
	 * @param syn
	 * @param workSpace
	 *            e.g. "/home/everest/ta/NOKOMGW-Ui5.0/testcases"
	 * @return
	 */
	public static BufferedReader run(String command, String workSpace) {
		log.debug("Terminal.run()");
		try {
			String[] cmd = new String[] { "/bin/bash", "-c", command };
			Process process = Runtime.getRuntime().exec(cmd, null, new File(workSpace));
			return new BufferedReader(new InputStreamReader(process.getInputStream()));
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}

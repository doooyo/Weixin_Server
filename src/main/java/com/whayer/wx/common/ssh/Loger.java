package com.whayer.wx.common.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class Loger {
  public static void main(String[] args) {
    String hostname = "10.9.187.203";
    String username = "root";
    String password = "arthura";

    try {
      /* Create a connection instance */

      Connection conn = new Connection(hostname);

      /* Now connect */

      conn.connect();

      /*
       * Authenticate. If you get an IOException saying something like
       * "Authentication method password not supported by the server at this stage." then please check the FAQ.
       */

      boolean isAuthenticated = conn.authenticateWithPassword(username, password);

      if (isAuthenticated == false)
        throw new IOException("Authentication failed.");

      /* Create a session */

      Session sess = conn.openSession();

      sess.execCommand("tail -f /var/opt/oss/log/NSN-common_mediations/oss_activity0_0.log");

      System.out.println("Here is some information about the remote host:");

      /*
       * This basic example does not handle stderr, which is sometimes dangerous (please read the FAQ).
       */

      InputStream stdout = new StreamGobbler(sess.getStdout());

      BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

      while (true) {
        String line = br.readLine();
        if (line == null)
          break;
        System.out.println(line);
      }

      /* Show exit status, if available (otherwise "null") */

      System.out.println("ExitCode: " + sess.getExitStatus());

      /* Close this session */

      sess.close();

      /* Close the connection */

      conn.close();

    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.err);
      System.exit(2);
    }
  }
}

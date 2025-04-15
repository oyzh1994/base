package cn.oyzh.ssh.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;

/**
 * @author oyzh
 * @since 2025-03-25
 */
public class SSHHolder {

   public static final JSch JSCH = new JSch();


   static {
      JSCH.setLogger(new Logger() {
         public void log(int level, String message) {
            if(message.contains("Disconnecting from")){
               System.out.println("Disconnecting from " + message);
            }
            System.err.println("JSchLog: " + message);
         }
         public boolean isEnabled(int level) { return true; }
      });
   }
}

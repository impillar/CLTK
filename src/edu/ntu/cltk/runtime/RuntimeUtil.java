package edu.ntu.cltk.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RuntimeUtil {

    /**
     * Execute one file
     *
     * @param executable
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void executeProcess(String... cmdArray) throws IOException, InterruptedException {
        executeProcess(false, cmdArray);
    }

    /**
     * Execute a process with message returned
     *
     * @param args
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String executeProcessWithResponse(String... args) throws IOException, InterruptedException {
        BufferedReader reader = null;
        Process pro = null;
        StringBuilder response = new StringBuilder();
        pro = Runtime.getRuntime().exec(args);
        pro.waitFor();
        reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            response.append(line + "\n");
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (pro != null) {
            try {
                pro.getInputStream().close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pro.destroy();
        }
        return response.toString();
    }

    /**
     * Execute a process with printing out the message determined by output
     *
     * @param output
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void executeProcess(boolean output, String... args) throws IOException, InterruptedException {
        BufferedReader reader = null;
        Process pro = null;

        pro = Runtime.getRuntime().exec(args);
        pro.waitFor();
        if (output) {
            reader =
                    new BufferedReader(new InputStreamReader(pro.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (pro != null) {
            try {
                pro.getInputStream().close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pro.destroy();
        }
    }

}

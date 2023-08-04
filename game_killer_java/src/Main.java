import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        String[] processes = {"AssassinsCreedIIGame.exe", "UbisoftGameLauncher.exe", "upc.exe", "UplayWebCore.exe", "steam.exe", "steamwebhelper.exe"};

        int countdownTime = 40 * 60;

        try {
            Runtime.getRuntime().exec("C:\\Program Files (x86)\\Steam\\steam.exe");
            Thread.sleep(15000);
            Runtime.getRuntime().exec("D:\\SteamLibrary\\steamapps\\common\\Assassin's Creed 2\\AssassinsCreedIIGame.exe");
            Thread.sleep(15000);

            while (countdownTime > 0) {
                Thread.sleep(1000);
                countdownTime--;
            }

            for (String process : processes) {
                findAndTerminateProcess(process);
            }

            UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "Время работы игры истекло", "Сообщение", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void findAndTerminateProcess(String processName) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(processName)) {
                    String[] splitLine = line.split("\\s+");
                    String pid = splitLine[1];
                    Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                }
            }
        }
    }
}

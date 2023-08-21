import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс Main содержит главный метод программы, который запускает игру Assassin's Creed II,
 * отслеживает время её работы и автоматически завершает процессы игры и связанных с ней приложений.
 */
public class Main {

    /**
     * Главный метод программы, который запускает игру и контролирует её время работы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) throws IOException {
        // Путь к файлу date.txt
        String filePath = "date_ac2.txt";

        // Проверяем, существует ли файл
        File file = new File(filePath);
        boolean fileExists = file.exists();

        // Если файл не существует, создаем его и записываем текущую дату
        if (fileExists) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String storedDate = reader.readLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = dateFormat.format(new Date());
                if (currentDate.equals(storedDate)) {
                    JOptionPane.showMessageDialog(null, "На сегодня доступ запрещен", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);

                }
            }
        }

        try (PrintWriter writer = new PrintWriter(filePath)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            writer.println(currentDate);
        }

        // Список процессов, которые будут завершены после окончания работы игры
        String[] processes = {"AssassinsCreedIIGame.exe", "UbisoftGameLauncher.exe", "upc.exe", "UplayWebCore.exe", "steam.exe", "steamwebhelper.exe"};

        // Время, в течение которого игра может работать (20 минут)
        int countdownTime = 20 * 60;

        try {
            // Запуск Steam
            Runtime.getRuntime().exec("C:\\Program Files (x86)\\Steam\\steam.exe");
            Thread.sleep(15000);
            // Запуск Assassin's Creed II
            Runtime.getRuntime().exec("D:\\SteamLibrary\\steamapps\\common\\Assassin's Creed 2\\AssassinsCreedIIGame.exe");
            Thread.sleep(15000);

            // Отсчет времени работы игры
            while (countdownTime > 0) {
                Thread.sleep(1000);
                countdownTime--;
            }

            // Завершение процессов игры и связанных с ней приложений
            for (String process : processes) {
                findAndTerminateProcess(process);
            }

            // Вывод сообщения о завершении работы игры
            UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            JOptionPane optionPane = new JOptionPane("Время работы игры истекло", JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog(frame, "Сообщение");
            dialog.setIconImage(null);
            dialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для поиска и завершения процесса по его имени.
     *
     * @param processName имя процесса для завершения
     * @throws IOException если возникнут проблемы при выполнении команды tasklist или taskkill
     */
    public static void findAndTerminateProcess(String processName) throws IOException {
        // Запуск команды tasklist для получения списка запущенных процессов
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
        Process process = processBuilder.start();

        // Чтение вывода команды tasklist для поиска процесса по его имени
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Если найден процесс с заданным именем, получаем его PID и завершаем процесс
                if (line.contains(processName)) {
                    String[] splitLine = line.split("\\s+");
                    String pid = splitLine[1];
                    Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                }
            }
        }
    }
}

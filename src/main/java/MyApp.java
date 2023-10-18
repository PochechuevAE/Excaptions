import java.io.*;
import java.util.*;

public class MyApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<String>> dataMap = new HashMap<>();

        while (true) {
            System.out.println("Введите данные в произвольном порядке (Фамилия Имя Отчество дата рождения номер телефона пол): ");
            String inputLine = scanner.nextLine();
            if (inputLine.isEmpty()) {
                break;
            }

            String[] inputData = inputLine.split(" ");

            try {
                if (validateInput(inputData)) {
                    String lastName = inputData[0];
                    if (!dataMap.containsKey(lastName)) {
                        dataMap.put(lastName, new ArrayList<>());
                    }
                    String formattedData = String.format("%s %s %s %s %s %s",
                            inputData[1], inputData[2], inputData[3], inputData[4], inputData[5], inputData[6]);
                    dataMap.get(lastName).add(formattedData);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }

        for (Map.Entry<String, List<String>> entry : dataMap.entrySet()) {
            String lastName = entry.getKey();
            List<String> dataList = entry.getValue();
            String fileName = lastName + ".txt";

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String data : dataList) {
                    writer.println(data);
                }
                System.out.println("Данные успешно записаны в файл: " + fileName);
            } catch (IOException e) {
                System.err.println("Ошибка при записи данных в файл " + fileName + ": " + e.getMessage());
            }
        }
    }

    private static boolean validateInput(String[] inputData) {
        if (inputData.length != 7) {
            throw new IllegalQuantityArgumentException("Неправильное количество данных. Введите: Фамилия Имя Отчество дата рождения номер телефона пол.");
        }

        if (!inputData[4].matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new IllegalDataArgumentException("Неверный формат даты. Используйте dd.mm.yyyy.");
        }

        try {
            Long.parseLong(inputData[5]);
        } catch (NumberFormatException e) {
            throw new IllegalPhoneArgumentException("Номер телефона должен быть целым числом.");
        }

        if (!inputData[6].equals("f") && !inputData[6].equals("m")) {
            throw new IllegalGenderArgumentException("Неверный пол. Используйте 'f' для женского и 'm' для мужского.");
        }

        return true;
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyApp3_0 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<String>> dataMap = new HashMap<>();

        while (true) {
            String lastName = getInput("Введите Фамилию:", scanner);
            String firstName = getInput("Введите Имя:", scanner);
            String middleName = getInput("Введите Отчество:", scanner);
            String birthDate = "";
            String phoneNumber = "";
            String gender = "";

            while (true) {
                birthDate = getInput("Введите дату рождения (в формате dd.mm.yyyy):", scanner);
                if (birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                    break;
                }
                System.err.println("Ошибка: Неверный формат даты. Используйте dd.mm.yyyy.");
            }

            while (true) {
                try {
                    phoneNumber = getInput("Введите номер телефона:", scanner);
                    Long.parseLong(phoneNumber);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: Номер телефона должен быть целым числом.");
                }
            }

            while (true) {
                gender = getInput("Введите пол (f/m):", scanner);
                if (gender.equals("f") || gender.equals("m")) {
                    break;
                }
                System.err.println("Ошибка: Неверный пол. Используйте 'f' для женского и 'm' для мужского.");
            }

            try {
                if (validateInput(lastName, firstName, middleName, birthDate, phoneNumber, gender)) {
                    if (!dataMap.containsKey(lastName)) {
                        dataMap.put(lastName, new ArrayList<>());
                    }
                    String formattedData = String.format("%s %s %s %s %s %s",
                            firstName, middleName, birthDate, phoneNumber, gender);
                    dataMap.get(lastName).add(formattedData);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }

            System.out.println("Желаете ввести еще данные? (y/n):");
            String continueInput = scanner.nextLine();
            if (!continueInput.equalsIgnoreCase("y")) {
                break;
            }
        }

        for (Map.Entry<String, List<String>> entry : dataMap.entrySet()) {
            String currentLastName = entry.getKey();
            List<String> dataList = entry.getValue();
            String fileName = currentLastName + ".txt";

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

    private static String getInput(String prompt, Scanner scanner) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.err.println("Ошибка: Поле не может быть пустым.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static boolean validateInput(String lastName, String firstName, String middleName,
                                         String birthDate, String phoneNumber, String gender) {
        return true;
    }
}


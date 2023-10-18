import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyApp2_0 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<String>> dataMap = new HashMap<>();
        String lastName = null;
        String firstName = null;
        String middleName = null;
        String birthDate = null;
        String phoneNumber = null;
        String gender = null;

        while (true) {
            System.out.println("Введите Фамилию:");
            lastName = scanner.nextLine();
            System.out.println("Введите Имя:");
            firstName = scanner.nextLine();
            System.out.println("Введите Отчество:");
            middleName = scanner.nextLine();
            System.out.println("Введите дату рождения (в формате dd.mm.yyyy):");
            birthDate = scanner.nextLine();
            System.out.println("Введите номер телефона:");
            phoneNumber = scanner.nextLine();
            System.out.println("Введите пол (f/m):");
            gender = scanner.nextLine();

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

            System.out.println("Желаете ввести еще данные? (y/n): ");
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

    private static boolean validateInput(String lastName, String firstName, String middleName,
                                         String birthDate, String phoneNumber, String gender) {
        if (lastName.isEmpty() || firstName.isEmpty() || middleName.isEmpty() || birthDate.isEmpty() ||
                phoneNumber.isEmpty() || gender.isEmpty()) {
            throw new IllegalQuantityArgumentException("Вы не ввели все необходимые данные.");
        }

        if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new IllegalDataArgumentException("Неверный формат даты. Используйте dd.mm.yyyy.");
        }

        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new IllegalPhoneArgumentException("Номер телефона должен быть целым числом.");
        }

        if (!gender.equals("f") && !gender.equals("m")) {
            throw new IllegalGenderArgumentException("Неверный пол. Используйте 'f' для женского и 'm' для мужского.");
        }

        return true;
    }
}

package ru.dima;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import ru.dima.console.CommandHandler;
import ru.dima.console.ConsoleReader;

public class Main {
    public static void main(String[] args) {
        ConsoleReader consoleReader = new ConsoleReader();
        CommandHandler handler = new CommandHandler();

        File file = new File(args[0]);
        createEmptyFile(file);

        String importCommand = "import " + args[0];
        handler.commandHandler(importCommand);
        System.out.println("Пользователь! Для начала работы, пожалуйста, ознакомьтесь со списком доступных команд, введя help!");

        while (true) {
            String command = consoleReader.read();
            handler.commandHandler(command);
        }
    }

    private static void createEmptyFile(File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Произошла ошибка при создании файла " + file.getAbsolutePath());
                System.exit(-1);
            }
        }

        try {
            PrintWriter pw = new PrintWriter(file);
            pw.write("[]");
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}



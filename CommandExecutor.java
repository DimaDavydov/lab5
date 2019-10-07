package ru.dima.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import ru.dima.model.UnnamedShorties;

public class CommandExecutor {
    private HashSet<UnnamedShorties> store = new HashSet<>();
    private Date date = new Date();
    private File file;

    {
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveFile));
    }

    /**
     * Removes shorties from collection
     * @param unnamedShorties shorties to remove
     */
    public void remove(UnnamedShorties unnamedShorties) {
        boolean isRemove = store.remove(unnamedShorties);
        if (isRemove) {
            System.out.println("Элемент удалён");
        } else {
            System.out.println("Элемент не найден");
        }
    }

    /**
     * Saves collection state into defined file
     * @param file file in which collection will be saved
     */
    public void save(File file) {
        this.file = file;
        saveFile();
    }

    /**
     * imports file
     * @param file which will be import
     */
    public void importFile(File file) {
        this.file = file;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            store = objectMapper.readValue(readFile(file), new TypeReference<HashSet<UnnamedShorties>>() {});
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке файла");
        }
    }

    /**
     * Saves file
     */
    private void saveFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            objectMapper.writeValue(outputStreamWriter, store);
        } catch (IOException e) {
            System.out.println("Не удалось сохранить коллекцию");
        }
    }

    /**
     * Adds element if it`s min element
     * @param unnamedShorties which will be added
     */
    public void addIfMin(UnnamedShorties unnamedShorties) {
        UnnamedShorties minElement = store.stream().sorted().findFirst().orElseThrow(NullPointerException::new);
        if (unnamedShorties.compareTo(minElement) < 0) {
            store.add(unnamedShorties);
            System.out.println("Элемент добавлен в коллекцию");
        } else {
            System.out.println("Элемент не добавлен в коллекцию");
        }
    }

    /**
     * Removes elements which less than specified
     * @param unnamedShorties which specified
     */
    public void removeLower(UnnamedShorties unnamedShorties) {
        int oldSize = store.size();
        store.removeIf((UnnamedShorties element) -> unnamedShorties.compareTo(element) > 0);
        int newSize = store.size();
        System.out.println("Количество удалённых элементов: " + (oldSize - newSize));
    }

    /**
     * Adds element into collection
     * @param unnamedShorties which will be added
     */
    public void add(UnnamedShorties unnamedShorties) {
        boolean isAdd = store.add(unnamedShorties);
        if (isAdd) {
            System.out.println("Элемент добавлен в коллекцию");
        } else {
            System.out.println("Такой элемент уже существует");
        }
    }

    /**
     * Shows collection
     */
    public void show() {
        if(store.size() == 0) {
            System.out.println("Коллекция пустая");
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            store.stream()
                 .sorted()
                 .map(shorties -> {
                     try {
                         return objectMapper.writeValueAsString(shorties);
                     } catch (JsonProcessingException e) { e.printStackTrace(); return null; }
                 })
                 .forEach(System.out::println);
        }
    }

    /**
     * Gives information about collection
     */
    public void info() {
        System.out.println("Информация о коллекции:\n" +
                "Тип: HashSet\n" +
                "Дата инициализации коллекции: " + date + "\n" +
                "Количество элементов: " + store.size());
    }

    /**
     * Close the program
     */
    public void exit() {
        saveFile();
        System.exit(0);
    }

    /**
     * Helps with commands
     */
    public void help() {
        System.out.println("Список доступных команд:\n" +
                "info - показать информацию о коллекции\n" +
                "show - показать все элементы коллекции\n" +
                "add {json} - добавить новый элемент в коллекцию\n" +
                "remove_lower {json} - удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "add_if_min {json} - добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "import {file} - загрузить коллекцию из файла\n" +
                "save {file} - сохранить коллекцию в файл\n" +
                "remove {json} - удалить элемент из коллекции\n" +
                "help - показать список доступных команд\n" +
                "exit - выйти из программы");
    }

    private String readFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine());
        }
        return result.toString();
    }
}

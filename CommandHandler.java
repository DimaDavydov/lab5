package ru.dima.console;

import ru.dima.parser.ParserCommand;

import java.io.IOException;

public class CommandHandler {

    private CommandExecutor executor = new CommandExecutor();

    public void commandHandler(String command) {
        command = command.trim();

        boolean commandIsEmpty = true;
        for(int i = 0; i < command.length(); ++i) {
            char c = command.charAt(i);

            if(c != ' ' && c != '\t' && c != '\n') {
                commandIsEmpty = false;
                break;
            }
        }

        if(commandIsEmpty) {
            return;
        }

        ParserCommand parser = new ParserCommand();
        Command parsedCommand;
        try {
            parsedCommand = parser.parseCommand(command);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке команды");
            return;
        }

        if(parsedCommand.getError() != null) {
            System.err.println(parsedCommand.getError().getErrorText());
            return;
        }

        switch (parsedCommand.getTypeCommand()) {
            case INFO:
                executor.info();
                break;
            case SHOW:
                executor.show();
                break;
            case ADD:
                if(parsedCommand.getArgument() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                if(parsedCommand.getArgument().getElement() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                executor.add(parsedCommand.getArgument().getElement());
                break;
            case REMOVE_LOWER:
                if(parsedCommand.getArgument() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                if(parsedCommand.getArgument().getElement() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                executor.removeLower(parsedCommand.getArgument().getElement());
                break;
            case ADD_IF_MIN:
                if(parsedCommand.getArgument() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                if(parsedCommand.getArgument().getElement() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                executor.addIfMin(parsedCommand.getArgument().getElement());
                break;
            case IMPORT:if(parsedCommand.getArgument() == null) {
                System.err.println("Необходимо передать файл!");
                break;
            }
                if(parsedCommand.getArgument().getFile() == null) {
                    System.err.println("Необходимо передать файл!");
                    break;
                }
                executor.importFile(parsedCommand.getArgument().getFile());
                break;
            case SAVE:
                executor.save(parsedCommand.getArgument().getFile());
                break;
            case REMOVE:
                if(parsedCommand.getArgument() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                if(parsedCommand.getArgument().getElement() == null) {
                    System.err.println("Необходимо передать элемент!");
                    break;
                }
                executor.remove(parsedCommand.getArgument().getElement());
                break;
            case HELP:
                executor.help();
                break;
            case EXIT:
                executor.exit();
                break;
            case NOT_A_COMMAND:
                System.err.println("Команды " + command + " не существует");
                break;
        }
    }
}

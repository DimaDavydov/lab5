package ru.dima.parser;

import ru.dima.console.Command;

import java.io.File;
import java.io.IOException;
import ru.dima.console.Command.TypeCommand;
import ru.dima.console.CommandError;
import ru.dima.model.AgeException;
import ru.dima.model.UnnamedShorties;

public class ParserCommand {
    private ParserJson parserJson = new ParserJson();

    public Command parseCommand(String crudeCommand) throws IOException {
        Command command = new Command();
        if (crudeCommand.contains(" ")) {
            String type = crudeCommand.substring(0, crudeCommand.indexOf(' '));
            String crudeArgument = crudeCommand.substring(crudeCommand.indexOf(' ') + 1);

            TypeCommand typeCommand = TypeCommand.fromString(type);
            command.setTypeCommand(typeCommand);

            Command.Argument argument = new Command.Argument();
            if (command.getTypeCommand() == Command.TypeCommand.IMPORT || command.getTypeCommand() == TypeCommand.SAVE) {
                argument.setFile(new File(crudeArgument));
            } else if (command.getTypeCommand() != TypeCommand.NOT_A_COMMAND){
                UnnamedShorties shorties = parserJson.fromJson(crudeArgument);

                try {
                    Integer age = shorties.getAge();
                    if(shorties.getWeight() == null
                        || age == null
                        || shorties.getShortyName() == null
                        || shorties.getShortyPosition() == null) {
                        command.setError(CommandError.INVALID_SHORTIES);
                    }
                } catch (AgeException e) {
                    e.printStackTrace();
                }

                argument.setElement(shorties);
            }
            command.setArgument(argument);
        } else {
            String type = crudeCommand;

            command.setTypeCommand(Command.TypeCommand.fromString(type.toUpperCase()));
        }
        return command;
    }
}

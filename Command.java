package ru.dima.console;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import ru.dima.model.UnnamedShorties;

@Setter
@Getter
public class Command {
    private TypeCommand typeCommand;
    private Argument argument;

    private CommandError error;

    @Setter
    @Getter
    public static class Argument {
        private UnnamedShorties element;
        private File file;
    }

    public enum TypeCommand {
        INFO,
        SHOW,
        ADD,
        REMOVE_LOWER,
        ADD_IF_MIN,
        IMPORT,
        SAVE,
        REMOVE,
        HELP,
        EXIT,
        NOT_A_COMMAND;

        private static Map<String, TypeCommand> map = Stream.of(TypeCommand.values())
            .collect(Collectors.toMap(type -> type.toString(), type -> type));

        public static TypeCommand fromString(String type) {
            TypeCommand typeCommand = map.get(type.toUpperCase());
            if(typeCommand != null) {
                return typeCommand;
            } else {
                return NOT_A_COMMAND;
            }
        }
    }
}

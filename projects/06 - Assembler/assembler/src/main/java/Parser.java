import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser {

    private Scanner scanner;
    private static final String NOT_COMMENT_OR_WSPACE = "^\\s*([^\\/\\*\\s]).*";
    private String currentLine;

    public Parser(String filename) {
        try {
            scanner = new Scanner(new FileReader(filename));
            scanner.useDelimiter(Pattern.compile("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void advance() {
        currentLine = null;
        while (hasMoreCommands()) {
            String nextLine = scanner.next();
            if (nextLine != null && !nextLine.trim().isEmpty() && !nextLine.trim().startsWith("/")) {
                currentLine = nextLine.replaceAll("\\s*//.*", "").trim();
                break;
            }
        }
    }

    public boolean hasMoreCommands() {
        return scanner.hasNext();

    }

    public Command commandType() {
        if (currentLine.startsWith("@"))
            return Command.A_COMMAND;
        if (currentLine.startsWith("("))
            return Command.L_COMMAND;
        return Command.C_COMMAND;
    }

    public String symbol() {
        if (commandType().equals(Command.A_COMMAND))
            return currentLine.substring(1);
        if (commandType().equals(Command.L_COMMAND))
            return currentLine.substring(1, currentLine.length() - 1);
        return null;
    }

    public String dest() {
        int equals = currentLine.indexOf('=');
        if (equals != -1)
            return currentLine.substring(0, equals);
        return "";
    }

    public String comp() {
        int from = currentLine.indexOf('=')+1;
        int to = currentLine.indexOf(';');
        if (to == -1) to = currentLine.length();
        return currentLine.substring(from, to);
    }

    public String jump() {
        int semicolon = currentLine.indexOf(';');
        if (semicolon != -1)
            return currentLine.substring(semicolon + 1);
        return "";
    }

}

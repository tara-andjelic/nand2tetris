import java.io.BufferedWriter;
import java.io.FileWriter;

public class Assembler {
    private static final int _16bit_zero = 0b10000000000000000;

    private Assembler() {
    }

    public static void assemble(String inputFile, String outputFile) {
        Parser parser = new Parser(inputFile);
        SymbolTable symbolTable = new SymbolTable();

        scanLabels(parser, symbolTable);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            int varAdrs = 16;
            parser = new Parser(inputFile);

            while (parser.hasMoreCommands()) {
                String out = null;
                parser.advance();
                Command command = parser.commandType();

                if (command.equals(Command.C_COMMAND)) {
                    out = "111" + Code.comp(parser.comp()) +
                            Code.dest(parser.dest()) +
                            Code.jump(parser.jump());

                } else if (command.equals(Command.A_COMMAND)) {
                    if (isNumber(parser.symbol())) {
                        out = to16Bit(Integer.parseInt(parser.symbol()));
                    } else if (symbolTable.contains(parser.symbol())) {
                        int labelAdrs = symbolTable.getAddress(parser.symbol());
                        out = to16Bit(labelAdrs);
                    } else {
                        symbolTable.addEntry(parser.symbol(), varAdrs);
                        out = to16Bit(varAdrs);
                        varAdrs++;
                    }
                }
                if (out != null) {
                    bw.write(out);
                    bw.newLine();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void scanLabels(Parser parser, SymbolTable symbolTable) {
        int instrAdrs = 0;
        try {
            while (parser.hasMoreCommands()) {
                parser.advance();
                Command command = parser.commandType();
                if (command.equals(Command.L_COMMAND)) {
                    symbolTable.addEntry(parser.symbol(), instrAdrs);
                } else {
                    instrAdrs++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumber(String symbol) {
        try {
            Integer.parseInt(symbol);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String to16Bit(int address) {
        String out = Integer.toBinaryString(_16bit_zero | address);
        return out.substring(1);
    }

    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = inputFile.replaceFirst(".asm", ".hack");
        try {
            Assembler.assemble(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package ua.javarush.encoder.cli;

import ua.javarush.encoder.cipher.CaesarCipher;
import ua.javarush.encoder.filesservice.FileService;
import ua.javarush.encoder.exception.IllegalArgumentRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class Cli {
    private char[] alphabet;

    public Cli(char[] alphabet) {

        this.alphabet = alphabet;
    }

    public void runConsole() {
        ConsoleViewProvider consoleViewProvider = new ConsoleViewProvider();
        consoleViewProvider.printIntoConsole("input command ENCRYPT/DECRYPT/BRUTE_FORCE:  ");
        String command = consoleViewProvider.read();
        consoleViewProvider.printIntoConsole("input path of file: ");
        String filename = consoleViewProvider.read();
        int key = 0;
        if (Commands.valueOf(command) != Commands.BRUTE_FORCE) {
            consoleViewProvider.printIntoConsole("input key: ");
            key = consoleViewProvider.readInt();
        }
        doCommand(command, filename, key);
    }

    public void runProgramWithArgs(String[] args) {
        String command = args[0];
        String filename = args[1];
        int key = 0;
        if (Commands.valueOf(command) != Commands.BRUTE_FORCE) {
            key = Integer.parseInt(args[2]);
        }
        doCommand(command, filename, key);
    }

    private void doCommand(String command, String filename, int key) {
        CaesarCipher caesarCipher = new CaesarCipher(alphabet);
        FileService fileService = new FileService();
        List<String> strings = fileService.read(filename);
        Commands commands = Commands.valueOf(command);
        switch (commands) {
            case ENCRYPT -> {
                List<String> encodeText = caesarCipher.encode(strings, key);
                fileService.write(filename + "[ENCRYPTED]", encodeText);
            }
            case DECRYPT -> {
                List<String> decodeText = caesarCipher.decode(strings, key);
                fileService.write(filename + "[DECRYPTED]", decodeText);
            }
            case BRUTE_FORCE -> {
                String decodedText = caesarCipher.brutForce(strings);
                if (decodedText.equals("Text is not decrypted")) {
                    System.out.println(decodedText);
                    return;
                } else {
                    String[] items = decodedText.split("\n");
                    List<String> textBruteForce = new ArrayList<>();
                    for (String item : items) {
                        textBruteForce.add(item);
                    }
                    fileService.write(filename + "[DECRYPTED]", textBruteForce);
                }
            }
            default -> {
                throw new IllegalArgumentRuntimeException("unknown command format");
            }
        }
    }
}





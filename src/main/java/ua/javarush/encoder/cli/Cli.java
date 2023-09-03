package ua.javarush.encoder.cli;

import ua.javarush.encoder.doCommand.Commands;
import ua.javarush.encoder.doCommand.DoCommand;
import ua.javarush.encoder.exception.FileNotExistRuntimeException;
import ua.javarush.encoder.exception.IllegalCommandRuntimeException;
import ua.javarush.encoder.exception.IllegalKeyRuntimeException;

import java.io.File;

public class Cli {
    private char[] alphabet;

    public Cli(char[] alphabet) {

        this.alphabet = alphabet;
    }

    public void runConsole() {
        ConsoleViewProvider consoleViewProvider = new ConsoleViewProvider();
        consoleViewProvider.printIntoConsole("input command ENCRYPT/DECRYPT/BRUTE_FORCE:  ");
        String command = consoleViewProvider.read();
        validateCommand(command);
        consoleViewProvider.printIntoConsole("input path of file: ");
        String filename = consoleViewProvider.read();
        validatePathOfFile (filename);
        int key = 0;
        if (Commands.valueOf(command)!= Commands.BRUTE_FORCE) {
            consoleViewProvider.printIntoConsole("input key: ");
            key = consoleViewProvider.readInt();
        }
        validateKey(key);
        giveCommand(command, filename, key);
    }

    public void runProgramWithArgs(String[] args) {
        String command = args[0];
        validateCommand(command);
        String filename = args[1];
        validatePathOfFile (filename);
        int key = 0;
        if (Commands.valueOf(command)!= Commands.BRUTE_FORCE) {
            key = Integer.parseInt(args[2]);
        }
        validateKey(key);
        giveCommand(command, filename, key);
    }

    private void giveCommand(String command, String filename, int key) {
        DoCommand doCommand = new DoCommand(alphabet);
        doCommand.executeCommand(command, filename, key);
    }

    private void validateCommand(String command) {
        if (!(command.equals("ENCRYPT")) && !(command.equals("DECRYPT"))
                && !(command.equals("BRUTE_FORCE"))) {
            throw new IllegalCommandRuntimeException("check the command and retype");
        }
    }

    private void validatePathOfFile(String fileName) {
        File file = new File(fileName);
        if (!(file.exists())) {
            throw new FileNotExistRuntimeException("The file doesn't exist");
        }
    }
    private void validateKey (int key){
        if (key<0) {
            throw new IllegalKeyRuntimeException("key value cannot be negative");
        }
    }
}

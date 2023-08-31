package ua.javarush.encoder.cli;

import java.util.Scanner;

public class ConsoleViewProvider {
    Scanner scanner = new Scanner(System.in);

    public String read() {
        return scanner.next();
    }

    public int readInt() {
        return scanner.nextInt();
    }

    public void printIntoConsole(String line) {
        System.out.println(line);
    }
}



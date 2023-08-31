package ua.javarush.encoder.runner;

import ua.javarush.encoder.cli.Cli;

public class Runner {
    public static void main(String[] args) {
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Cli cli = new Cli(alphabet);
        if (args.length == 0) {
            cli.runConsole();
        } else if (args.length == 2 || args.length == 3) {
            cli.runProgramWithArgs(args);
        }
    }
}




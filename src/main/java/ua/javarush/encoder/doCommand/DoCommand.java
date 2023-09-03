package ua.javarush.encoder.doCommand;

import ua.javarush.encoder.brutForce.BruteForce;
import ua.javarush.encoder.cipher.CaesarCipher;
import ua.javarush.encoder.exception.IllegalArgumentRuntimeException;
import ua.javarush.encoder.filesservice.FileService;

import java.util.ArrayList;
import java.util.List;

public class DoCommand {
    CaesarCipher caesarCipher = new CaesarCipher();
    FileService fileService = new FileService();

    public DoCommand (char[] alphabet) {
        caesarCipher.setAlphabet(alphabet);
    }

    public void executeCommand(String command, String filename, int key) {
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
                BruteForce brutForce = new BruteForce(caesarCipher.getAlphabet());
                String decodedText = brutForce.decodeBrutForce(strings);
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

package ua.javarush.encoder.brutForce;

import ua.javarush.encoder.cipher.CaesarCipher;
import ua.javarush.encoder.cli.ConsoleViewProvider;
import ua.javarush.encoder.exception.InOutRuntimeException;
import ua.javarush.encoder.exception.NotFoundRuntimeException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class BruteForce {

    CaesarCipher caesarCipher = new CaesarCipher();

    public BruteForce(char[] alphabet) {
        caesarCipher.setAlphabet(alphabet);
    }

    public String decodeBrutForce(List<String> strings) {
        String decodedText = null;
        String text = String.join("\n", strings);
        for (int i = 0; i < caesarCipher.getAlphabet().length; i++) {
            decodedText = caesarCipher.getCipherString(i, text);
            ConsoleViewProvider consoleViewProvider = new ConsoleViewProvider();
            if (analyzeDecodedText(decodedText)) {
                consoleViewProvider.printIntoConsole("Check the text: ");
                consoleViewProvider.printIntoConsole(decodedText.substring(0, 30));
                consoleViewProvider.printIntoConsole("Is the text decoded? (enter Y to confirm or any symbol to continue selection) ");
                String read = consoleViewProvider.read();
                if (read.equalsIgnoreCase("Y")) {
                    break;
                } else {
                    consoleViewProvider.printIntoConsole("key picking continues....");
                    consoleViewProvider.printIntoConsole("");

                }
            } else {
                decodedText = "Text is not decrypted";
            }
        }
        return decodedText;
    }


    private boolean analyzeDecodedText(String decodedText) {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("src/main/resources/propertyFiles/frequency_letter.properties")) {
            properties.load(fileReader);
            Set<String> statisticLetters = properties.stringPropertyNames();
            char[] elementDecodedText = decodedText.toCharArray();
            int countCoincideWithStatisticLettters = 0;
            for (String statisticLetter : statisticLetters) {
                int countOccurrenceLetter = 0;
                char[] key1 = statisticLetter.toCharArray();
                double statistic = Double.parseDouble(properties.getProperty(statisticLetter));
                for (int j = 0; j < elementDecodedText.length; j++) {
                    if (Character.toLowerCase(key1[0]) == Character.toLowerCase(elementDecodedText[j])) {
                        countOccurrenceLetter++;
                    }
                }
                double statisticLetterInText = ((double) countOccurrenceLetter / elementDecodedText.length) * 100.0;
                double deltaStatistic = Math.abs(statisticLetterInText - statistic);
                if (deltaStatistic <= (statistic / 100) * 20) {
                    countCoincideWithStatisticLettters++;
                }
            }
            boolean possibleText = false;
            if (countCoincideWithStatisticLettters >= 4) {
                possibleText = true;
            }
            return possibleText;

        } catch (FileNotFoundException e) {
            throw new NotFoundRuntimeException(e);
        } catch (IOException e) {
            throw new InOutRuntimeException(e);
        }
    }
}

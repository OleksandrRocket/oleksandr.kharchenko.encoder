package ua.javarush.encoder.cipher;

import ua.javarush.encoder.cli.ConsoleViewProvider;
import ua.javarush.encoder.exception.InOutRuntimeException;
import ua.javarush.encoder.exception.NotFoundRuntimeException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class CaesarCipher {
    private char[] alphabet;

    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public List<String> encode(List<String> strings, int key) {
        return cipherText(strings, key);
    }

    public List<String> decode(List<String> strings, int key) {
        return cipherText(strings, -key);
    }

    private List<String> cipherText(List<String> strings, int key) {
        int keyInRange = key % alphabet.length;
        List<String> cipherStrings = new ArrayList<>();
        String[] elements = strings.toArray(new String[strings.size()]);
        for (String element : elements) {
            String cipherElements = getCipherString(keyInRange, element);
            cipherStrings.add(cipherElements);
        }
        return cipherStrings;
    }

    private String getCipherString(int keyInRange, String element) {
        String cipherElements;
        char[] cipherSymbols = element.toCharArray();
        for (int i = 0; i < cipherSymbols.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                if (cipherSymbols[i] == alphabet[j]) {
                    int jValidate = validationOverflow(keyInRange, j);
                    cipherSymbols[i] = alphabet[jValidate];
                    break;
                }
            }
        }
        cipherElements = new String(cipherSymbols);
        return cipherElements;
    }

    private int validationOverflow(int keyInRange, int j) {
        int jValidated = 0;
        if (j + keyInRange >= alphabet.length) {
            int countToEnd = alphabet.length - j;
            jValidated = keyInRange - countToEnd;
        } else if (j + keyInRange < 0) {
            int countToZero = j;
            int remainderKey = Math.abs(keyInRange + countToZero);
            jValidated = alphabet.length - remainderKey;
        } else {
            jValidated = j + keyInRange;
        }
        return jValidated;
    }

    public String brutForce(List<String> strings) {
        String decodedText = null;
        String text = String.join("\n", strings);
        for (int i = 0; i < alphabet.length; i++) {
            decodedText = getCipherString(i, text);
            ConsoleViewProvider consoleViewProvider = new ConsoleViewProvider();
            if (analyzeDecodedText(decodedText)) {
                consoleViewProvider.printIntoConsole("Check the text: ");
                consoleViewProvider.printIntoConsole(decodedText.substring(0, 30));
                consoleViewProvider.printIntoConsole("Is the text decoded? (enter Y to confirm or any symbol to continue selection) ");
                String read = consoleViewProvider.read();
                if (read.equalsIgnoreCase("Y")) {
                    break;
                }else {
                    System.out.println("key picking continues....");
                    System.out.println();
                }
            }else {
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
                double statisticLetterInText = ((double) countOccurrenceLetter / elementDecodedText.length) * 100.0 ;
                double deltaStatistic = Math.abs(statisticLetterInText - statistic);
                if (deltaStatistic <= (statistic/100)*20) {
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







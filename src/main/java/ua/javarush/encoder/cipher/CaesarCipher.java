package ua.javarush.encoder.cipher;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher {
    private char[] alphabet;

    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
    }
    public CaesarCipher() {
    }

    public char[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public List<String> encode(List<String> strings, int key) {
        return getCipherText(strings, key);
    }

    public List<String> decode(List<String> strings, int key) {
        return getCipherText(strings, -key);
    }

    private List<String> getCipherText(List<String> strings, int key) {
        int keyInRange = key % alphabet.length;
        List<String> cipherStrings = new ArrayList<>();
        String[] elements = strings.toArray(new String[strings.size()]);
        for (String element : elements) {
            String cipherElements = getCipherString(keyInRange, element);
            cipherStrings.add(cipherElements);
        }
        return cipherStrings;
    }

    public String getCipherString(int keyInRange, String element) {
        String cipherElements;
        char[] cipherSymbols = element.toCharArray();
        for (int i = 0; i < cipherSymbols.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                if (cipherSymbols[i] == alphabet[j]) {
                    int indexOverflow = getIndexOverflow(keyInRange, j);
                    cipherSymbols[i] = alphabet[indexOverflow];
                    break;
                }
            }
        }
        cipherElements = new String(cipherSymbols);
        return cipherElements;
    }

    private int getIndexOverflow(int keyInRange, int indAlphabet) {
        int indexOverflow = 0;
        if (indAlphabet + keyInRange >= alphabet.length) {
            int countToEnd = alphabet.length - indAlphabet;
            indexOverflow = keyInRange - countToEnd;
        } else if (indAlphabet + keyInRange < 0) {
            int countToZero = indAlphabet;
            int remainderKey = Math.abs(keyInRange + countToZero);
            indexOverflow = alphabet.length - remainderKey;
        } else {
            indexOverflow = indAlphabet + keyInRange;
        }
        return indexOverflow;
    }
}

package ua.javarush.encoder;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher {
    private static char[] alphabet;
    private static int key;

    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public static List<String> encode (List<String> strings, int key) {
        return moveElement(strings, key);

    }

    public static List<String> decode(List<String> strings, int key) {
        return moveElement(strings, -key);
    }

    private static List<String> moveElement(List<String> textList, int key) {
        List<String> moveTextList = new ArrayList<>();
        CaesarCipher.key = key % alphabet.length;
        String[] textArray = textList.toArray(new String[textList.size()]);
        for (String stringText : textArray) {
            char[] stringTextCharArray = stringText.toCharArray();
            for (int i = 0; i < stringTextCharArray.length; i++) {
                for (int j = 0; j < alphabet.length; j++) {
                    if (stringTextCharArray[i] == alphabet[j]) {
                        stringTextCharArray[i] = alphabet[j + CaesarCipher.key];
                        break;
                    }
                }
            }
            String moveText = new String(stringTextCharArray);
            moveTextList.add(moveText);
        }
        return moveTextList;
    }
}
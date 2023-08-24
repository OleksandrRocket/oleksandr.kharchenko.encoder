package ua.javarush.encoder;

import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {


        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        Scanner scn = new Scanner(System.in);
        CaesarCipher caesarCipher = new CaesarCipher(alphabet);
        FileService fileService = new FileService();

        String fileName = args[1];

//      System.out.println("put your  name file for read: ");
//      String fileName = scn.next()

        List<String> strings = fileService.read(fileName);

        List<String> encodeText = CaesarCipher.encode(strings, Integer.parseInt(args[2]));
//      List<String> decodeText = CaesarCipher.decode(strings, Integer.parseInt(args[2]));

        System.out.println("put your  name file for write: ");
        fileName = scn.next();
        fileService.write(fileName, encodeText);
    }

}



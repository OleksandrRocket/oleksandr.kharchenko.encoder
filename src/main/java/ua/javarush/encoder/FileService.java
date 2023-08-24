package ua.javarush.encoder;


import ua.javarush.encoder.Exception.InOutException;
import ua.javarush.encoder.Exception.NotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public List<String> read(String nameFile) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(nameFile))) {
            List<String> strings = new ArrayList<>();
            while (buffer.ready()) {
                strings.add(buffer.readLine());
            }
            return strings;
        } catch (FileNotFoundException e) {
            throw new NotFoundException("File not found");
        } catch (IOException e) {
            throw new InOutException("Unknown exception IO");
        }
    }


    public void write(String fileName, List<String> lines) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            String[] linesArray = lines.toArray(new String[lines.size()]);
            for (String line : linesArray) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();

            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new InOutException("Unknown exception IO");
        }

    }
}
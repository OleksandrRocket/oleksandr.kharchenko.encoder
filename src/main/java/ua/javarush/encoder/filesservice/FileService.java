package ua.javarush.encoder.filesservice;

import ua.javarush.encoder.exception.InOutRuntimeException;
import ua.javarush.encoder.exception.NotFoundRuntimeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public List<String> read(String fileName) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            List<String> strings = new ArrayList<>();
            while (buffer.ready()) {
                strings.add(buffer.readLine());
            }
            return strings;
        } catch (FileNotFoundException e) {
            throw new NotFoundRuntimeException(e);
        } catch (IOException e) {
            throw new InOutRuntimeException(e);
        }
    }

    public void write(String fileName, List<String> stringsForWrite) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            String[] lines = stringsForWrite.toArray(new String[stringsForWrite.size()]);
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new InOutRuntimeException(e);
        }
    }
}
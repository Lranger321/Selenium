package nlp;

import java.io.*;

public class FileWorker {

    public String getTextFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder text = new StringBuilder();
        try {
            String line = bufferedReader.readLine();
            do {
                text.append(line)
                        .append(System.lineSeparator());
                line = bufferedReader.readLine();
            } while ((line != null));
        } finally {
            bufferedReader.close();
        }
        return text.toString();
    }

    public void writeToFile(String text, String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(text);
        bufferedWriter.close();
    }
}

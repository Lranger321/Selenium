package nlp;

import java.io.IOException;
import java.util.Map;

public class NlpMain {

    private static final String FILE_PATH = "src/main/resources/text.txt";
    private static final String RESULT_DIRECTORY_PATH = "result/";

    public static void main(String[] args) throws IOException {
        FileWorker fileWorker = new FileWorker();
        NlpWorker nlpWorker = NlpWorker.getInstance();
        String text = fileWorker.getTextFromFile(FILE_PATH);
        fileWorker.createResultDirectory();
        writeArrayToFile(fileWorker, RESULT_DIRECTORY_PATH + "tokens", nlpWorker.getTokensFromText(text));
        writeArrayToFile(fileWorker, RESULT_DIRECTORY_PATH + "sentences", nlpWorker.getSentences(text));
        writeMapToFile(fileWorker, RESULT_DIRECTORY_PATH + "lemmas", nlpWorker.getLemmasAndStemers(text));
    }


    private static void writeArrayToFile(FileWorker worker, String filePath, String[] array) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            sb.append(str)
                    .append("\n");
        }
        worker.writeToFile(sb.toString(), filePath);
    }

    private static void writeMapToFile(FileWorker worker, String filePath, Map<String, String> map) throws IOException {
        StringBuilder sb = new StringBuilder();
        map.forEach((stemmer, lemma) -> {
            sb.append("Stemmer: ").append(stemmer)
                    .append("\n")
                    .append("Lemma: ").append(lemma)
                    .append("\n");
        });
        worker.writeToFile(sb.toString(), filePath);
    }

}

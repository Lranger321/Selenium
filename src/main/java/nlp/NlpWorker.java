package nlp;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class NlpWorker {

    private static SentenceDetectorME sentenceDetectorME;
    private static TokenizerME tokenizerME;
    private static POSModel posModel;
    private static DictionaryLemmatizer dictionaryLemmatizer;
    private static NlpWorker instance;

    public static NlpWorker getInstance() throws IOException {
        if (instance == null) {
            InputStream is = new FileInputStream("src/main/resources/bins/en-sent.bin");
            sentenceDetectorME = new SentenceDetectorME(new SentenceModel(is));
            InputStream inputStream = new FileInputStream("src/main/resources/bins/en-token.bin");
            tokenizerME = new TokenizerME(new TokenizerModel(inputStream));
            InputStream inputStreamPOSTagger = new FileInputStream("src/main/resources/bins/en-pos-maxent.bin");
            posModel = new POSModel(inputStreamPOSTagger);
            InputStream dictLemmatizer = new FileInputStream("src/main/resources/bins/en-lemmatizer.dict");
            dictionaryLemmatizer = new DictionaryLemmatizer(dictLemmatizer);
            instance = new NlpWorker();
        }
        return instance;
    }

    public String[] getSentences(String text) {
        return sentenceDetectorME.sentDetect(text);
    }

    public String[] getTokensFromText(String text) {
        return tokenizerME.tokenize(text);
    }

    public Map<String, String> getLemmasAndStemers(String text) {
        Map<String, String> result = new TreeMap<>();
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String[] tokens = getTokensFromText(text);
        String[] tags = posTagger.tag(tokens);
        String[] lemmas = dictionaryLemmatizer.lemmatize(tokens, tags);
        for (int i = 0; i < lemmas.length && i < tags.length; i++) {
            if (!lemmas[i].equals("O")) {
                result.put(tokens[i], lemmas[i]);
            }
        }
        return result;
    }
}

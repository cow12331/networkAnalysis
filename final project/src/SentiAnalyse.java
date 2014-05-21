import com.aliasi.util.Files;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.ScoredClassifier;

import com.aliasi.lm.NGramProcessLM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 
 *use for classify twitter
 *only myEvaluate useful
 */
public class SentiAnalyse {
	File mPolarityDir;
	String[] mCategories = {"pos", "neg"};
	DynamicLMClassifier<NGramProcessLM> mClassifier;

	SentiAnalyse() {
		int nGram = 8;
		mClassifier = DynamicLMClassifier
				.createNGramProcess(mCategories, nGram);
	}
	//evaluate by existed model
	public static String myEvaluate(String tweet) throws IOException, Exception {
		String modelFile = "tclassifier";
		ScoredClassifier<CharSequence> compiledClassifier = null;
		ObjectInputStream oi = new ObjectInputStream(new FileInputStream(modelFile));
		compiledClassifier = (ScoredClassifier<CharSequence>) oi.readObject();
		oi.close();
			String review = tweet;
			Classification classification = compiledClassifier.classify(review);
			if (classification.bestCategory().equals("pos")) {
				return "pos";
			}
			else if (classification.bestCategory().equals("neg")) {
				return "neg";
			} 
			else {
				return "wrong";
			}
	}
}

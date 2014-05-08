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

public class SentiAnalyse {

	File mPolarityDir;
	String[] mCategories = {"pos", "neg"};
	DynamicLMClassifier<NGramProcessLM> mClassifier;

	SentiAnalyse() {
		int nGram = 8;
		mClassifier = DynamicLMClassifier
				.createNGramProcess(mCategories, nGram);
	}

	void run() throws Exception {
		//train();
		String out = myEvaluate("Tristel Plc PT Raised to GBX 63 $TSTL http://t.co/lv3sNp4vSU");
		System.out.print(out);
	}

	boolean isTrainingFile(File file) {
		return file.getName().charAt(2) != '9'; // test on fold 9
	}
	
	void train() throws IOException {
		int numTrainingCases = 0;
		int numTrainingChars = 0;
		System.out.println("\nTraining.");
		for (int i = 0; i < mCategories.length; ++i) {
			String category = mCategories[i];
			Classification classification = new Classification(category);
			File file = new File(mPolarityDir, mCategories[i]);
			File[] trainFiles = file.listFiles();
			for (int j = 0; j < trainFiles.length; ++j) {
				File trainFile = trainFiles[j];
				if (isTrainingFile(trainFile)) {
					++numTrainingCases;
					String review = Files.readFromFile(trainFile, "ISO-8859-1");
					numTrainingChars += review.length();
					Classified<CharSequence> classified = new Classified<CharSequence>(
							review, classification);
					mClassifier.handle(classified);
				}
			}
		}

		String modelFile = "tclassifier";
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(
				modelFile));
		mClassifier.compileTo(os);
		os.close();
		System.out.println("  # Training Cases=" + numTrainingCases);
		System.out.println("  # Training Chars=" + numTrainingChars);
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
				//System.out.println(tweet + " pos ");
				return "pos";
			}
			else if (classification.bestCategory().equals("neg")) {
				//System.out.println(tweet + " neg ");
				return "neg";
			} 
			else {
				//System.out.println(tweet + " wrong ");
				return "wrong";
			}
	}

	public static void main(String[] args) {
		try {
			new SentiAnalyse().run();
		} catch (Throwable t) {
			System.out.println("Thrown: " + t);
			t.printStackTrace(System.out);
		}
	}

}

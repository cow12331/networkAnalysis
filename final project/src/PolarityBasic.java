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
 * training model 
 * output model file name is tclassifier
 * show test outcome
 */
public class PolarityBasic {

	File mPolarityDir;
	String[] mCategories;
	DynamicLMClassifier<NGramProcessLM> mClassifier;

	PolarityBasic(String[] args) {
		System.out.println("\nBASIC POLARITY DEMO");
		mPolarityDir = new File(args[0], "txt_sentoken2");
		System.out.println("\nData Directory=" + mPolarityDir);
		mCategories = mPolarityDir.list();
		int nGram = 3;
		mClassifier = DynamicLMClassifier
				.createNGramProcess(mCategories, nGram);
	}

	void run() throws Exception {
		train();
		evaluate();
	}

	boolean isTrainingFile(File file) {
		return file.getName().charAt(4) != '2'; //chose different files
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

	void evaluate() throws IOException {
		System.out.println("\nEvaluating.");
		int numTests = 0;
		int numCorrect = 0;
		for (int i = 0; i < mCategories.length; ++i) {
			String category = mCategories[i];
			File file = new File(mPolarityDir, mCategories[i]);
			File[] trainFiles = file.listFiles();
			for (int j = 0; j < trainFiles.length; ++j) {
				File trainFile = trainFiles[j];
				if (!isTrainingFile(trainFile)) {
					String review = Files.readFromFile(trainFile, "ISO-8859-1");
					++numTests;
					Classification classification = mClassifier
							.classify(review);
					if (classification.bestCategory().equals(category))
						++numCorrect;
				}
			}
		}
		System.out.println("  # Test Cases=" + numTests);
		System.out.println("  # Correct=" + numCorrect);
		System.out.println("  % Correct=" + ((double) numCorrect)
				/ (double) numTests);
	}

	public static void main(String[] args) {
		try {
			String[] test = { "E:/poly" };
			new PolarityBasic(test).run();
		} catch (Throwable t) {
			System.out.println("Thrown: " + t);
			t.printStackTrace(System.out);
		}
	}
}

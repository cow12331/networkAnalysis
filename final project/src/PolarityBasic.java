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
		//myEvaluate2();
	}

	boolean isTrainingFile(File file) {
		return file.getName().charAt(4) != '8'; // test on fold 9
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

	//original 
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

	//
	void myEvaluate() throws IOException {
		System.out.println("\nEvaluating.");
		int numTests = 0;
		int numPos = 0;
		int numNeg = 0;

		FileReader fr = new FileReader("test.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";

		while ((line = br.readLine()) != null) {
			String review = line;
			++numTests;
			Classification classification = mClassifier.classify(review);

			if (classification.bestCategory().equals("pos")) {
				System.out.println(line + " pos ");
				++numPos;
			} else {
				System.out.println(line + " neg ");
				++numNeg;
			}
		}
		System.out.println(" =============================================== ");
		System.out.println("  # Test Cases=" + numTests);
		System.out.println("  # numPos=" + numPos);
		System.out.println("  # numNeg=" + numNeg);
	}

	//evaluate by existed model
	void myEvaluate2() throws IOException, Exception {
		System.out.println("\nEvaluating.");
		int numTests = 0;
		int numPos = 0;
		int numNeg = 0;

		String modelFile = "tclassifier";
		ScoredClassifier<CharSequence> compiledClassifier = null;
		ObjectInputStream oi = new ObjectInputStream(new FileInputStream(modelFile));
		compiledClassifier = (ScoredClassifier<CharSequence>) oi.readObject();
		oi.close();

		FileReader fr = new FileReader("test.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";

		while ((line = br.readLine()) != null) {
			String review = line;
			++numTests;
			Classification classification = compiledClassifier.classify(review);

			if (classification.bestCategory().equals("pos")) {
				System.out.println(line + " pos ");
				++numPos;
			} else {
				System.out.println(line + " neg ");
				++numNeg;
			}
		}
		System.out.println(" =============================================== ");
		System.out.println("  # Test Cases=" + numTests);
		System.out.println("  # numPos=" + numPos);
		System.out.println("  # numNeg=" + numNeg);
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

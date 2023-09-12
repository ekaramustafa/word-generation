package main;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import zemberek.morphology.generator.WordGenerator.Result;

public class Utils {
	private static final String[] number = {"A3sg","A3pl"};

	private static final String[] possessives = {"P1sg","P2sg","P3sg","P1pl","P2pl","P3pl"};
	
//	private static final String[] cases = {"Nom","Loc","Dat","Abl","Ins","Gen","Equ"};
	private static final String[] cases = {"Nom","Loc","Dat","Abl","Ins","Gen"};
	
//	private static final String[] derivationSuffixes = {"Dim","Ness","With","Without","Related","JustLike","Rel","Agt","Become","Acquire"};
	private static final String[] derivationSuffixes = {"With","Without","Rel"};
	
//	private static final String[] tenses = {"Past","Narr","Cond","Prog1","Prog2","Aor","Fut","Imp","Opt","Desr","Neces","Pres"};
	private static final String[] tenses = {"Past","Narr","Cond","Desr"};
	
	
	public static String resultString(List<Result> results, String ...parameters) {
		StringBuilder resultStr = new StringBuilder(generatePermutation(parameters));
	    results.forEach(s->resultStr.append(s.surface).append(" "));
	    resultStr.append("\n");
	    return resultStr.toString();
	}
	
	public static String generatePermutation(String ...parameters) {
		StringBuilder combination = new StringBuilder();
		for(int i =0;i<parameters.length-1;i++) {
			combination.append(parameters[i]).append(" + ");
		}
		combination.append(parameters[parameters.length-1]).append(" = ");
		return combination.toString();
	}
	
	
	public static String[] selectMethod(Methods method) {
		if(method == Methods.NUMBER) {
			return number;
		}
		else if (method == Methods.CASE) {
			return cases;
		}
		else if (method== Methods.DERIVATION) {
			return derivationSuffixes;
		}
		else if (method == Methods.POSSESIVE) {
			return possessives;
		}
		else if(method == Methods.TENSES){
			return tenses;
		}
		return null;
	}
	
	private static void writeToTxt(String text,String filepath) {
		
		try{
			File file = new File(filepath);
            File parentDir = file.getParentFile();

            // Create the parent directories if they don't exist.
            if (!parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Failed to create directories for the file.");
                }
            }
		
	        try (Writer writer = new BufferedWriter(new FileWriter(filepath, true))) {
	            // Open the file for writing (existing content will be overwritten).
	           
	            // Write the text to the file.
	            writer.write(text);
	            
	            System.out.println("Text has been written to the file successfully.");
	        } catch (IOException e) {
	            System.err.println("An error occurred while writing to the file: " + e.getMessage());
	        }
		}
        
		catch(IOException e) 
		{
    		
    	}
	}
	
	///OutputName
	public static void writeToTxt(String text,String word, int numberOfSuffixes) {
		String filename = word + "_" + String.valueOf(numberOfSuffixes) + ".txt";
        String filepath = "words\\"+word +"\\" + filename; // Specify the name of the file you want to create/write to.
        writeToTxt(text,filepath);
        
	}
	
	public static List<String> readWords(){
		List<String> result = new ArrayList<>();
        // Define the file path

        try {
            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(WordGenerator.DEFAULT_LEXICON_PATH);

            // Create a BufferedReader to efficiently read the file line by line
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            // Read and print each line in the file until the end of the file is reached
            while ((line = bufferedReader.readLine()) != null) {
            	String[] arrOfStr = line.split(" ");
                result.add(arrOfStr[0]);
            }

            // Close the BufferedReader and FileReader
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    

        return result;
	}
	

}

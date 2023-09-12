package main;

import java.io.IOException;
import java.nio.file.Paths;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.lexicon.RootLexicon;

public class Test {
	private static final String DEFAULT_LEXICON_PATH = "C:\\Users\\ebu\\Desktop\\Lexicon\\words.txt";

	private static RootLexicon lexicon;
	private static TurkishMorphology morphology;
	
	public static void main(String[] args) {
		
		try {
			lexicon = RootLexicon.builder()
				      .setLexicon(RootLexicon.getDefault()) // start with default
				      .addTextDictionaries(Paths.get(DEFAULT_LEXICON_PATH)) // add from file
				      .build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		morphology = TurkishMorphology.builder()
				    .setLexicon(lexicon)
				    .disableCache()
				    .build();
		 System.out.println(morphology.getLexicon().getMatchingItems("oku"));
	}

}

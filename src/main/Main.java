package main;
import java.io.BufferedReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.generator.WordGenerator.Result;
import zemberek.morphology.lexicon.DictionaryItem;
import zemberek.morphology.lexicon.RootLexicon;

public class Main {


	public static void main(String[] args) throws IOException {

		List<String> words = Utils.readWords();
		WordGenerator wordGenerator = new WordGenerator(null);
		for(int j=0;j<words.size();j++) {
			wordGenerator.setItem(words.get(j));
			for(int i =0;i<JsonConfig.maxNumberOfSuffixes;i++) {
				wordGenerator.generateWords(i+1);
			}
		}
		
		
	}
	

}

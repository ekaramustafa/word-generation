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
	
	public enum Methods{
		NUMBER,
		POSSESIVE,
		CASE,
		DERIVATION,
		TENSES;
		
	}
	
	
	private static final String path = "C:\\Users\\ebu\\Desktop\\New folder\\words.txt";
	
//	private static final String[] number = {"A1sg","A2sg","A3sg","A1pl","A2pl","A3pl"};
	private static final String[] number = {"A3sg","A3pl"};

	private static final String[] possessives = {"P1sg","P2sg","P3sg","P1pl","P2pl","P3pl"};
	
//	private static final String[] cases = {"Nom","Loc","Dat","Abl","Ins","Gen","Equ"};
	private static final String[] cases = {"Nom","Loc","Dat","Abl","Ins","Gen"};
	
//	private static final String[] derivationSuffixes = {"Dim","Ness","With","Without","Related","JustLike","Rel","Agt","Become","Acquire"};
	private static final String[] derivationSuffixes = {"With","Without","Rel"};
	
//	private static final String[] tenses = {"Past","Narr","Cond","Prog1","Prog2","Aor","Fut","Imp","Opt","Desr","Neces","Pres"};
	private static final String[] tenses = {"Past","Narr","Cond","Desr"};

	
	private static RootLexicon lexicon;
	private static TurkishMorphology morphology;


	public static void main(String[] args) throws IOException {
		
		lexicon = RootLexicon.builder()
			      .setLexicon(RootLexicon.getDefault()) // start with default
			      .addTextDictionaries(Paths.get(path)) // add from file
			      .build();
		 
		morphology = TurkishMorphology.builder()
				    .setLexicon(lexicon)
				    .disableCache()
				    .build();
		DictionaryItem item = morphology.getLexicon().getMatchingItems("mbb").get(0);
		test(item,3);
	}
	
	
	private static void test(DictionaryItem item,int permutation) {
		//one generation method
		switch(permutation) {
			case 1:
				oneGenerationMethod(item);
				break;
			case 2:
				twoGenerationMethod(item);
				break;
			case 3:
				threeGenerationMethod(item);
				break;
			case 4:
				fourGenerationMethod(item);
				break;
		}

		
	}
	
	private static void oneGenerationMethod(DictionaryItem item) 
	{
		Methods[] methods = Methods.values();
		for(int i =0;i<methods.length;i++) {
			List<String> wrongPermutations = new ArrayList<>();
			List<String> correctPermutations = new ArrayList<String>();
			String[] method = selectMethod(methods[i]);
			String result = calculateFirstPossibilities(item, method, wrongPermutations,correctPermutations);
			analyze(result,methods[i],wrongPermutations,correctPermutations);
		}
	}
	
	
	private static void twoGenerationMethod(DictionaryItem item) {
		Methods[] methods = Methods.values();
		//two generation method
		for(int i =0;i<methods.length;i++) {

			for(int j=0;j<methods.length;j++) {
				if(i == j)continue;
				List<String> wrongPermutations = new ArrayList<>();
				List<String> correctPermutations = new ArrayList<String>();
				List<String> correctPermutations2 = new ArrayList<String>();
				String[] method = selectMethod(methods[i]);
				String[] method2 = selectMethod(methods[j]);
				
				String result = calculateTwoPossibilities(item, method,method2, wrongPermutations,correctPermutations,correctPermutations2);
				analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2);
			}


		}
	}
	
	
	private static void threeGenerationMethod(DictionaryItem item) {
		Methods[] methods = Methods.values();
		//two generation method
		for(int i =0;i<methods.length;i++) {

			for(int j=0;j<methods.length;j++) {
				if(i == j)continue;
				
				
				for(int k=0;k<methods.length;k++) {
					if(i == k || j == k)continue;
					List<String> wrongPermutations = new ArrayList<>();
					List<String> correctPermutations = new ArrayList<String>();
					List<String> correctPermutations2 = new ArrayList<String>();
					List<String> correctPermutations3 = new ArrayList<String>();
					String[] method = selectMethod(methods[i]);
					String[] method2 = selectMethod(methods[j]);
					String[] method3 = selectMethod(methods[k]);
					String result = calculateThreePossibilities(item, method,method2,method3, wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3);
					analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3);
				}
			}
		}
	}
	
	private static void fourGenerationMethod(DictionaryItem item) {
		Methods[] methods = Methods.values();
		//two generation method
		for(int i =0;i<methods.length;i++) {

			for(int j=0;j<methods.length;j++) {
				if(i == j)continue;
				
				for(int k=0;k<methods.length;k++) {
					if(i == k || j == k)continue;
					for(int m=0;m<methods.length;m++) {
						if(m == i || m == j || m == k)continue;
						
						List<String> wrongPermutations = new ArrayList<>();
						List<String> correctPermutations = new ArrayList<String>();
						List<String> correctPermutations2 = new ArrayList<String>();
						List<String> correctPermutations3 = new ArrayList<String>();
						List<String> correctPermutations4 = new ArrayList<String>();
						
						String[] method = selectMethod(methods[i]);
						String[] method2 = selectMethod(methods[j]);
						String[] method3 = selectMethod(methods[k]);
						String[] method4 = selectMethod(methods[m]);
						
						String result = calculateFourPossibilities(item, method,method2,method3,method4, wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4);
						analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4);
					}
					
				}
			}
		}
	}
	
	private static void fiveGenerationMethod(DictionaryItem item) {
		Methods[] methods = Methods.values();
		//two generation method
		for(int i =0;i<methods.length;i++) {

			for(int j=0;j<methods.length;j++) {
				if(i == j)continue;
				
				for(int k=0;k<methods.length;k++) {
					if(i == k || j == k)continue;
					for(int m=0;m<methods.length;m++) {
						if(m == i || m == j || m == k)continue;
						for(int l = 0 ; l<methods.length; l++) {
							List<String> wrongPermutations = new ArrayList<>();
							List<String> correctPermutations = new ArrayList<String>();
							List<String> correctPermutations2 = new ArrayList<String>();
							List<String> correctPermutations3 = new ArrayList<String>();
							List<String> correctPermutations4 = new ArrayList<String>();
							List<String> correctPermutations5 = new ArrayList<String>();
							
							String[] method = selectMethod(methods[i]);
							String[] method2 = selectMethod(methods[j]);
							String[] method3 = selectMethod(methods[k]);
							String[] method4 = selectMethod(methods[m]);
							String[] method5 = selectMethod(methods[l]);
							
							String result = calculateFivePossibilities(item, method,method2,method3,method4,method5, wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4,correctPermutations5);
							analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4,correctPermutations5);
						}
						
						
					}
					
				}
			}
		}
	}
	
	
	private static void analyze(String result, Methods method,List<String> wrongP,List<String> ...correctP) {
		System.out.println("==========START========");
		System.out.println("For generating w/ " + method.toString());
		if(result.length() == 0) {
			System.out.println("==========END=========");
			return;
		}
		System.out.println(result);
		System.out.println();
		//System.out.println("Wrong Permutations : " + wrongP);
		for(List<String> list : correctP) {
			System.out.println(list);
			
		}
		
		System.out.println("==========END=========");
		System.out.println();
		
		
	}
	
	
	private static String[] selectMethod(Methods method) {
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
	

	
	private static String calculateFirstPossibilities(DictionaryItem item,String[] list, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			List<Result> results = morphology.getWordGenerator().generate(item, element);
			
			if(results.size() == 0) 
			{
				String key = generatePermutation(element).replace(" = ", "");
				lists[0].add(key);
			}
			else 
			{
			  	resultStr.append(resultString(results,element));
			  	if(!lists[1].contains(element)) lists[1].add(element);
			}
	  	}
		return resultStr.toString();
	}
	
	
	private static String calculateTwoPossibilities(DictionaryItem item,String[] list,String[] list2, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				List<Result> results = morphology.getWordGenerator().generate(item, element,element2);
				
				if(results.size() == 0) 
				{
					String key = generatePermutation(element,element2).replace(" = ","");
					lists[0].add(key);
				}
				else 
				{
				  	resultStr.append(resultString(results,element,element2));
				  	if(!lists[1].contains(element)) lists[1].add(element);
				  	if(!lists[2].contains(element2)) lists[2].add(element2);
				}
				
			}
			
			
	  	}
		return resultStr.toString();
	}
	
	private static String calculateThreePossibilities(DictionaryItem item,String[] list,String[] list2, String[] list3, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				for(String element3 : list3) {
					List<Result> results = morphology.getWordGenerator().generate(item, element,element2,element3);
					
					if(results.size() == 0) 
					{
						String key = generatePermutation(element,element2,element3).replace(" = ","");
						lists[0].add(key);
					}
					else 
					{
					  	resultStr.append(resultString(results,element,element2,element3));
					  	if(!lists[1].contains(element)) lists[1].add(element);
					  	if(!lists[2].contains(element2)) lists[2].add(element2);
					  	if(!lists[3].contains(element3)) lists[3].add(element3);
					}
				}
				
			}
			
			
	  	}
		return resultStr.toString();
	}
	
	private static String calculateFourPossibilities(DictionaryItem item,String[] list,String[] list2, String[] list3, String[] list4, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				for(String element3 : list3) {
					for(String element4: list4) {
						List<Result> results = morphology.getWordGenerator().generate(item, element,element2,element3,element4);
						
						if(results.size() == 0) 
						{
							String key = generatePermutation(element,element2,element3,element4).replace(" = ","");
							lists[0].add(key);
						}
						else 
						{
						  	resultStr.append(resultString(results,element,element2,element3,element4));
						  	if(!lists[1].contains(element)) lists[1].add(element);
						  	if(!lists[2].contains(element2)) lists[2].add(element2);
						  	if(!lists[3].contains(element3)) lists[3].add(element3);
						  	if(!lists[4].contains(element4)) lists[4].add(element4);
						}
					}
				}
				
			}
			
			
	  	}
		return resultStr.toString();
	}
	
	private static String calculateFivePossibilities(DictionaryItem item,String[] list,String[] list2, String[] list3, String[] list4, String[] list5, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				for(String element3 : list3) {
					for(String element4: list4) {
						for(String element5 : list5) {

							List<Result> results = morphology.getWordGenerator().generate(item, element,element2,element3,element4,element5);
							
							if(results.size() == 0) 
							{
								String key = generatePermutation(element,element2,element3,element4,element5).replace(" = ","");
								lists[0].add(key);
							}
							else 
							{
							  	resultStr.append(resultString(results,element,element2,element3,element4,element5));
							  	if(!lists[1].contains(element)) lists[1].add(element);
							  	if(!lists[2].contains(element2)) lists[2].add(element2);
							  	if(!lists[3].contains(element3)) lists[3].add(element3);
							  	if(!lists[4].contains(element4)) lists[4].add(element4);
							  	if(!lists[5].contains(element5)) lists[5].add(element5);
							}
						}
					}
				}
				
			}
			
			
	  	}
		return resultStr.toString();
	}
	
	
	
	private static void calculateFivePossibilities(DictionaryItem item, List<String> ...lists) {
		List<String> iteratedFirst = lists[0];
		List<String> iteratedSecond = lists[1];
		List<String> iteratedThird = lists[2];
		List<String> iteratedFourth = lists[3];
		List<String> iteratedFifth = lists[4];
		for (String numberM : iteratedFirst) {
			  for (String possessiveM : iteratedSecond) {
				  for(String caseM : iteratedThird) {
					  for(String deriviationM: iteratedFourth) {
						  for(String tenseM : iteratedFifth) {
							  List<Result> results = morphology.getWordGenerator().generate(item, numberM, possessiveM,caseM,deriviationM, tenseM);
							if(results.size() == 0) 
							{
								String key = generatePermutation(numberM,possessiveM,caseM,deriviationM,tenseM);
								lists[0].add(key);
							}
							else {
							  	resultString(results,numberM,possessiveM,caseM,deriviationM,tenseM);
							  	if(!lists[1].contains(numberM)) lists[1].add(numberM);
							  	if(!lists[2].contains(possessiveM)) lists[2].add(possessiveM);
							  	if(!lists[3].contains(caseM)) lists[3].add(caseM);
							  	if(!lists[4].contains(deriviationM)) lists[4].add(deriviationM);
							  	if(!lists[5].contains(tenseM)) lists[5].add(tenseM);
								}
						  	}
					  	}
				  	}
			  	}
		}
	}
	
	
	private static String resultString(List<Result> results, String ...parameters) {
		StringBuilder resultStr = new StringBuilder(generatePermutation(parameters));
	    results.forEach(s->resultStr.append(s.surface).append(" "));
	    resultStr.append("\n");
	    return resultStr.toString();
	}
	
	private static String generatePermutation(String ...parameters) {
		StringBuilder combination = new StringBuilder();
		for(int i =0;i<parameters.length-1;i++) {
			combination.append(parameters[i]).append(" + ");
		}
		combination.append(parameters[parameters.length-1]).append(" = ");
		return combination.toString();
	}

	

}

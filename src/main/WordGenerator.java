package main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.generator.WordGenerator.Result;
import zemberek.morphology.lexicon.DictionaryItem;
import zemberek.morphology.lexicon.RootLexicon;

public class WordGenerator {
	
	
	private RootLexicon lexicon;
	private TurkishMorphology morphology;
	private DictionaryItem item;
	private int numberOfSuffixes;
	private String word;

	
	public WordGenerator(String word,String LEXICON_PATH) {

		try {
			lexicon = RootLexicon.builder()
				      .setLexicon(RootLexicon.getDefault()) // start with default
				      .addTextDictionaries(Paths.get(Path.DEFAULT_LEXICON_PATH)) // add from file
				      .build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		morphology = TurkishMorphology.builder()
				    .setLexicon(lexicon)
				    .disableCache()
				    .build();
		if(word != null)
			setItem(word);
		//Load the config file
		Utils.readParams();
	}
	
	public WordGenerator(String word) {
		this(word,Path.DEFAULT_LEXICON_PATH);
	}
	
	public void setItem(String word) {
		item = morphology.getLexicon().getMatchingItems(word).get(0);

	}

	
	public void generateWords(int numberOfSuffixes) {
		//one generation method
		this.numberOfSuffixes = numberOfSuffixes;
		switch(numberOfSuffixes) {
			case 1:
				oneGenerationMethod();
				break;
			case 2:
				twoGenerationMethod();
				break;
			case 3:
				threeGenerationMethod();
				break;
			case 4:
				fourGenerationMethod();
				break;
			default:
				System.err.println("ERROR : Please enter valid numberOfSuffixes" );
				System.out.println("Possible number of suffixes is from 1 to " + String.valueOf(JsonConfig.maxNumberOfSuffixes));
		}
	

		
	}
	private void analyze(String result, Methods method,List<String> wrongP,List<String> ...correctP) {
		if(result.length() == 0)return;
		System.out.println("==========START========");
		System.out.println("For generating w/ " + method.toString());

		System.out.println(result);
		Utils.writeToTxt(result,item.root,numberOfSuffixes);
		System.out.println();
		//System.out.println("Wrong Permutations : " + wrongP);
		for(List<String> list : correctP) {
			System.out.println(list);
			
		}
		
		System.out.println("==========END=========");
		System.out.println();
		
		
	}
	
	private void oneGenerationMethod() 
	{
		Methods[] methods = Methods.values();
		for(int i =0;i<methods.length;i++) {
			List<String> wrongPermutations = new ArrayList<>();
			List<String> correctPermutations = new ArrayList<String>();
			String[] method = Utils.selectMethod(methods[i]);
			String result = calculateFirstPossibilities(method, wrongPermutations,correctPermutations);
			analyze(result,methods[i],wrongPermutations,correctPermutations);
		}
	}
	
	
	private  void twoGenerationMethod() {
		Methods[] methods = Methods.values();
		//two generation method
		for(int i =0;i<methods.length;i++) {

			for(int j=0;j<methods.length;j++) {
				if(i == j)continue;
				List<String> wrongPermutations = new ArrayList<>();
				List<String> correctPermutations = new ArrayList<String>();
				List<String> correctPermutations2 = new ArrayList<String>();
				String[] method = Utils.selectMethod(methods[i]);
				String[] method2 = Utils.selectMethod(methods[j]);
				
				String result = calculateTwoPossibilities(method,method2, wrongPermutations,correctPermutations,correctPermutations2);
				analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2);
			}


		}
	}
	
	
	private void threeGenerationMethod() {
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
					String[] method = Utils.selectMethod(methods[i]);
					String[] method2 = Utils.selectMethod(methods[j]);
					String[] method3 = Utils.selectMethod(methods[k]);
					String result = calculateThreePossibilities(method,method2,method3, wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3);
					analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3);
				}
			}
		}
	}
	
	private void fourGenerationMethod() {
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
						
						String[] method = Utils.selectMethod(methods[i]);
						String[] method2 = Utils.selectMethod(methods[j]);
						String[] method3 = Utils.selectMethod(methods[k]);
						String[] method4 = Utils.selectMethod(methods[m]);
						
						String result = calculateFourPossibilities(method,method2,method3,method4, wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4);
						analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4);
					}
					
				}
			}
		}
	}
	
	private void fiveGenerationMethod() {
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
							if(l == i || l == j || l == m || l == k)continue;
							List<String> wrongPermutations = new ArrayList<>();
							List<String> correctPermutations = new ArrayList<String>();
							List<String> correctPermutations2 = new ArrayList<String>();
							List<String> correctPermutations3 = new ArrayList<String>();
							List<String> correctPermutations4 = new ArrayList<String>();
							List<String> correctPermutations5 = new ArrayList<String>();
							
							String[] method = Utils.selectMethod(methods[i]);
							String[] method2 = Utils.selectMethod(methods[j]);
							String[] method3 = Utils.selectMethod(methods[k]);
							String[] method4 = Utils.selectMethod(methods[m]);
							String[] method5 = Utils.selectMethod(methods[l]);
							
							String result = calculateFivePossibilities(method,method2,method3,method4,method5, wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4,correctPermutations5);
							analyze(result,methods[i],wrongPermutations,correctPermutations,correctPermutations2,correctPermutations3,correctPermutations4,correctPermutations5);
						}
						
						
					}
					
				}
			}
		}
	}
	
	


	
	private String calculateFirstPossibilities(String[] list, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			List<Result> results = morphology.getWordGenerator().generate(item, element);
			
			if(results.size() == 0) 
			{
				String key = Utils.generatePermutation(element).replace(" = ", "");
				lists[0].add(key);
			}
			else 
			{
			  	resultStr.append(Utils.resultString(results,element));
			  	if(!lists[1].contains(element)) lists[1].add(element);
			}
	  	}
		return resultStr.toString();
	}
	
	
	private String calculateTwoPossibilities(String[] list,String[] list2, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				List<Result> results = morphology.getWordGenerator().generate(item, element,element2);
				
				if(results.size() == 0) 
				{
					String key = Utils.generatePermutation(element,element2).replace(" = ","");
					lists[0].add(key);
				}
				else 
				{
				  	resultStr.append(Utils.resultString(results,element,element2));
				  	if(!lists[1].contains(element)) lists[1].add(element);
				  	if(!lists[2].contains(element2)) lists[2].add(element2);
				}
				
			}
			
			
	  	}
		return resultStr.toString();
	}
	
	private String calculateThreePossibilities(String[] list,String[] list2, String[] list3, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				for(String element3 : list3) {
					List<Result> results = morphology.getWordGenerator().generate(item, element,element2,element3);
					
					if(results.size() == 0) 
					{
						String key = Utils.generatePermutation(element,element2,element3).replace(" = ","");
						lists[0].add(key);
					}
					else 
					{
					  	resultStr.append(Utils.resultString(results,element,element2,element3));
					  	if(!lists[1].contains(element)) lists[1].add(element);
					  	if(!lists[2].contains(element2)) lists[2].add(element2);
					  	if(!lists[3].contains(element3)) lists[3].add(element3);
					}
				}
				
			}
			
			
	  	}
		return resultStr.toString();
	}
	
	private String calculateFourPossibilities(String[] list,String[] list2, String[] list3, String[] list4, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				for(String element3 : list3) {
					for(String element4: list4) {
						List<Result> results = morphology.getWordGenerator().generate(item, element,element2,element3,element4);
						
						if(results.size() == 0) 
						{
							String key = Utils.generatePermutation(element,element2,element3,element4).replace(" = ","");
							lists[0].add(key);
						}
						else 
						{
						  	resultStr.append(Utils.resultString(results,element,element2,element3,element4));
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
	
	private String calculateFivePossibilities(String[] list,String[] list2, String[] list3, String[] list4, String[] list5, List<String> ...lists) {
		StringBuilder resultStr = new StringBuilder();
		
		for (String element : list) {
			for(String element2: list2) {
				for(String element3 : list3) {
					for(String element4: list4) {
						for(String element5 : list5) {

							List<Result> results = morphology.getWordGenerator().generate(item, element,element2,element3,element4,element5);
							
							if(results.size() == 0) 
							{
								String key = Utils.generatePermutation(element,element2,element3,element4,element5).replace(" = ","");
								lists[0].add(key);
							}
							else 
							{
							  	resultStr.append(Utils.resultString(results,element,element2,element3,element4,element5));
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
	
	
	
	private void calculateFivePossibilities(List<String> ...lists) {
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
								String key = Utils.generatePermutation(numberM,possessiveM,caseM,deriviationM,tenseM);
								lists[0].add(key);
							}
							else {
							  	Utils.resultString(results,numberM,possessiveM,caseM,deriviationM,tenseM);
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
	


	public RootLexicon getLexicon() {
		return lexicon;
	}


	public void setLexicon(RootLexicon lexicon) {
		this.lexicon = lexicon;
	}


	public TurkishMorphology getMorphology() {
		return morphology;
	}


	public void setMorphology(TurkishMorphology morphology) {
		this.morphology = morphology;
	}
	
	public DictionaryItem getItem() {
		return item;
	}


	public void setItem(DictionaryItem item) {
		this.item = item;
	}
	


}

package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cipher {
	
	private ArrayList<Character> alphabet;
	
	
	public Cipher(){
		this.alphabet=this.createAlphabet();
	}
	
	/*
	 * Encryption function
	 */
	public String encryptText(String plainText, String key){
		String filledText = addFilling(plainText,key);
		ArrayList<String> blocked = makeBlocks(filledText,key);
		ArrayList<String> shuffledBlocks = transposeBlocks(blocked);
		String encryptedText = transposeChars(shuffledBlocks,key);
		System.out.println("Plain Text : "+plainText);
		System.out.println("Encrypted Text : "+encryptedText);
		return encryptedText;
	}
	
	/*
	 * Decryption function
	 */
	public String decryptText(String encryptedText, String key){
		ArrayList<String> deBlocks = new ArrayList<String>();
		deBlocks = makeBlocks(encryptedText,key);
		String decryptedText = retransposeChars(retransposeBlocks(deBlocks),key);
		System.out.println("Decrypted Text : "+decryptedText);
		return decryptedText;
	}
	
	/*
	 * Generates the initial alphabet
	 */
	private ArrayList<Character> createAlphabet(){
		ArrayList<Character> alph = new ArrayList<Character>();
		
		for (int i=32;i<127;i++){
			if(i!=92){
				alph.add((char)i);
			}
		}
		System.out.println(alph.toString());
		return alph;
	}
	
	/*
	 * Pading is added to the plain test
	 */
	private String addFilling(String plainText, String key){
		int originalTextLength = plainText.length();
		if(originalTextLength % key.length() != 0){
			for (int j=0;j<(key.length()-(originalTextLength%key.length()));j++){
				plainText += '#';
			}
		}
		System.out.println("After padding added : "+plainText);
		return plainText;
	} 
	
	/*
	 * Plain text is broken into blocks of fixed length
	 */
	private ArrayList<String> makeBlocks(String plainText, String key){
		ArrayList<String> blocks = new ArrayList<String>();
		for (int k = 0; k < plainText.length(); k += key.length()) {
			blocks.add((plainText.substring(k, k+key.length())));
		}
		System.out.println("After braking to blocks : "+blocks.toString());
		return blocks;
	} 
	
	/*
	 * Blocks are interleaved
	 */
	private ArrayList<String> transposeBlocks(ArrayList<String> blocks){
		ArrayList <String> shuffledBlocks = new ArrayList <String>();
		for (int evenBlock=0; evenBlock<blocks.size(); evenBlock += 2){
			shuffledBlocks.add((String) blocks.get(evenBlock));
		}
		for (int oddBlock=1; oddBlock<blocks.size(); oddBlock += 2){
			shuffledBlocks.add((String) blocks.get(oddBlock));
		}
		System.out.println("After shifting blocks : "+shuffledBlocks);
		return shuffledBlocks;
	}
	
	/*
	 * Rearranges interleaved blocks
	 */
	private ArrayList<String> retransposeBlocks(ArrayList<String> blocks){
		ArrayList <String> arrangedBlocks = new ArrayList <String>();
		List<String> part1 = new ArrayList <String>();
		List<String> part2 = new ArrayList <String>();
		if(blocks.size()%2==0){
			part1 = blocks.subList(0,(blocks.size()/2));
			part2 = blocks.subList((blocks.size()/2), blocks.size());
			for (int x=0;x<part1.size();x++){
				arrangedBlocks.add(part1.get(x));
				arrangedBlocks.add(part2.get(x));
			}
		}else{
			part1 = blocks.subList(0,(blocks.size()/2)+1);
			part2 = blocks.subList((blocks.size()/2)+1, blocks.size());
			for (int x=0;x<part2.size();x++){
				arrangedBlocks.add(part1.get(x));
				arrangedBlocks.add(part2.get(x));
			}arrangedBlocks.add(part1.get(part1.size()-1));
		}
		System.out.println("After rearranging blocks : "+arrangedBlocks);
		return arrangedBlocks;
	}
	
	/*
	 * Each character in the text is transposed to a mapping character from the alphabet.
	 * Mapping character for each character is generated using key and alphabet.
	 */
	private String transposeChars(ArrayList<String> shuffledBlocks,String key){
		String encryptedText = "";
		for (Iterator<String> i = shuffledBlocks.iterator(); i.hasNext();) {
		    String block = i.next();
		    for (int s=0;s<key.length();s++){
		    	char textChar = block.charAt(s);
		    	char keyChar = key.charAt(s);
		    	int newIndex = (this.alphabet.indexOf(textChar)+this.alphabet.indexOf(keyChar))%94;
		    	encryptedText += this.alphabet.get(newIndex);
		    }
		}
		System.out.println("After transposing chars : "+encryptedText);
		return encryptedText;
	}
	
	/*
	 * Inverts the transposing function
	 */
	private String retransposeChars(ArrayList<String> blocks,String key){
		String decryptedText = "";
		for (Iterator<String> i = blocks.iterator(); i.hasNext();) {
		    String block = i.next();
		    for (int d=0;d<key.length();d++){
		    	char cryptChar = block.charAt(d);
		    	char keyChar = key.charAt(d);
		    	int newIndex = (this.alphabet.indexOf(cryptChar)+94-this.alphabet.indexOf(keyChar))%94;
		    	decryptedText += this.alphabet.get(newIndex);
		    }
		}
		System.out.println("After transposing chars : "+decryptedText);
		return decryptedText;
	}

}

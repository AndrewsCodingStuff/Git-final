
import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Commit {

	private static String parent;
	private static String other;
	private static String summary;
	private static String author;
	private static String date;
	private static String fileName;
	
	public static void main (String[]args) throws IOException, NoSuchAlgorithmException{
		Commit the = new Commit("Fix", "Andrew", null);
		the.makeTree();
		//System.out.println("went through");
	}
	
	public Commit(String s, String a, String pointer) throws FileNotFoundException {
		summary = s;
		author = a;
		date = getDate();
		if (pointer != null)
			parent = pointer;
		else
			parent = null;
		other = null;
		
		String contents = summary + date + author + parent;
		fileName = getSHA1(contents);
		
		writeFile();
		
		if (parent != null) {
			Scanner input = new Scanner(new File("test/objects/" + parent));
			String pContents = "";
			pContents += input.nextLine() + "\n";
			pContents += input.nextLine() + "\n";
			pContents += fileName + "\n";
			input.nextLine();
			pContents += input.nextLine() + "\n";
			pContents += input.nextLine() + "\n";
			pContents += input.nextLine() + "\n";
			
			PrintWriter pw = new PrintWriter("test/objects/" + parent);
			pw.append(pContents);
			pw.close();
		}
	}
	
	public String nameOfCommit() {
		return fileName;
	
	}
	
	public void makeTree() throws IOException, NoSuchAlgorithmException {
		ArrayList<String> indexContents = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("index.txt"));
		ArrayList<String> treeContents = new ArrayList<String>();
	while(br.ready()) {
		indexContents.add(br.readLine());
	}
	for(int i = 0; i<indexContents.size(); i++) {
		String toFix = indexContents.get(i);
		System.out.println(toFix);
		for(int a = 0; a<toFix.length(); a++) {
			System.out.println(toFix.charAt(a));
			
		if(toFix.charAt(a) == ':') {
		String newStr = toFix.substring(0,a);
		treeContents.add(newStr);
		
			
		}
		
		}
		
	}
	br.close();
	
	Tree ofCommit = new Tree(treeContents);
	

	}
		
	
	
	private String getSHA1(String str){
		String value = str;
		String output = "";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(value.getBytes("utf8"));
			output = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception exception){
			exception.printStackTrace();
		}

		return output; 
	}
	
	public static String getDate() {
		return "" + LocalDateTime.now();
	}
	
	public void writeFile() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("test/objects/" + new File(fileName));
		pw.append(fileName + "\n");
		if (parent != null)
			pw.append(parent + "\n");
		if (other != null)
			pw.append(other + "\n");
		pw.append(author + "\n");
		pw.append(date + "\n");
		pw.append(summary + "\n");
		pw.close();
	}
	
	public String getCommitName() {
		return fileName;
	}
	
	public String getChild() {
		return other;
	}
	
	public void setChild(Commit child) {
		other = child.getCommitName();
	}
	
	public String getParent() {
		return parent;
	}
	
	public void setParent(Commit par) {
		parent = par.getCommitName();
	}
	
}

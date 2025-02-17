
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
	private static String nameOfTree;
	private ArrayList<String> tc = new ArrayList<String>();
	private ArrayList<String> forDelete = new ArrayList<String>();
	private String Head;

	
	public static void main (String[]args) throws IOException, NoSuchAlgorithmException{
		
		Index inx = new Index();
		inx.init();
		inx.add("foo.txt");
		inx.add("bar.txt");
		Commit parent = new Commit("The Test", "Andrew G", null);
		inx.add("something.txt");
		Commit com1 = new Commit("B Test", "Andrew", parent.getCommitName());
		inx.add("foobar.txt");
		Commit com2 = new Commit("Third commit", "Andy", com1.getCommitName());
		inx.add("tester.txt");
		Commit com3 = new Commit("4th commit", "And", com2.getCommitName());
		assertTrue()
		//com3.deleteFile(com3.getTree(), "foo.txt");  
		//com1.makeTree();
		//System.out.println("went through");
	}
	
	public Commit(String s, String a, String pointer) throws NoSuchAlgorithmException, IOException {
		
		
	
		summary = s;
		author = a;
		date = getDate();
		if (pointer != null)
			parent = pointer;
		else
			parent = null;
			other = null;
		
		String contents = summary + date + author + parent + nameOfTree + other;
		fileName = getSHA1(contents);
		Head = fileName;
		//PrintWriter headwrite = new PrintWriter("test/" + "Head");
		makeTree();
		writeFile();
		PrintWriter write = new PrintWriter("Head");
		write.flush();
		write.println(Head);
		write.close();
		
		if (parent != null) {
			Scanner input = new Scanner(new File("test/objects/" + parent));
			String pContents = "";
			/*if(input.hasNextLine()) {
				pContents += input.nextLine() + "\n";
			}*/
			pContents += input.nextLine() + "\n";
			//System.out.println(pContents);
			pContents += input.nextLine() + "\n";
			//System.out.println(pContents);

			pContents += fileName + "\n";
			//System.out.println(pContents);

			input.nextLine();
			pContents += input.nextLine() + "\n";
			//System.out.println(pContents);

			pContents += input.nextLine() + "\n";
			pContents += input.nextLine() + "\n";
			
			PrintWriter pw = new PrintWriter("test/objects/" + parent);
			pw.append(pContents);
			pw.close();
		}
	}
	
	
	public void makeTree() throws IOException, NoSuchAlgorithmException {
		ArrayList<String> indexContents = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("test/index.txt"));
		ArrayList<String> treeContents = new ArrayList<String>();
		
	while(br.ready()) {
		indexContents.add(br.readLine());
	}
	for(int i = 0; i<indexContents.size(); i++) {
		String toFix = indexContents.get(i);
		//System.out.println(toFix);
		for(int a = 0; a<toFix.length(); a++) {
			//System.out.println(toFix.charAt(a));
			
		if(toFix.charAt(a) == ':') {
		String newStr = toFix.substring(a);
		newStr = "blob: " + newStr;
		treeContents.add(newStr);
		PrintWriter writer = new PrintWriter("test/index.txt");
		writer.flush();
		writer.close();
		 
			
		}
		
		}
		
	}
	br.close();
	
	if(parent!= null) {
		System.out.println(parent);
		treeContents.add("tree: " + readFirstLine(parent));
		
	}
	Tree ofCommit = new Tree(treeContents);
	nameOfTree = ofCommit.getTreeName();
	//File deleteIndexFile = new FIle("index.txt");
	tc = treeContents;

	}
		
	
	
	//Look for the file by checking each tree, then 
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
		pw.append(getTree() + "\n");
		
		if (parent != null)
			pw.append(parent + "\n");
		else
			pw.append("\n");
		if (other != null)
			pw.append(other + "\n");
		else
			pw.append("\n");
		
		pw.append(author + "\n");
		pw.append(date + "\n");
		pw.append(summary + "\n");
		pw.close();
	}
	//the recursive call is the tree before.
	public void deleteFile(String theTree, String deleteFile) throws IOException {
		//String treeToRead = getTree();
		String findFile = "Blob: " + getSHA1(deleteFile);
		Boolean check = false;
		System.out.println(theTree);
		String last = readLastLine(theTree);
		BufferedReader reader = new BufferedReader(new FileReader("test/objects/" + theTree));
		while(reader.ready()) {
			String str = reader.readLine();
			if(str.equals(last)) {
				deleteFile(last,deleteFile);
			}
		forDelete.add(str);
		if(str.equals(findFile)) {
			check = true;
			
			return;
			//return check;
			
		}
		
		}
	
		
	}
	
	public void editFile(String editFile) {
		//deleteFile(getTree,)
	}
	
	
	// once u find the tree that has the bad file then u get all the blobs before it and add it to the tree
	// u also add the tree after the bad tree which.
	//for edit u delete that one and add a blob.
	
	
		
	
	
	public String readFirstLine(String fileName) throws IOException {
		String str = "";
	
		BufferedReader read = new BufferedReader(new FileReader("test/objects/" + fileName));
		str = read.readLine();
		return str;
	}
	
	public String readLastLine(String fileName) throws IOException {
		String str = "";
		BufferedReader read = new BufferedReader(new FileReader("test/objects/" + fileName));
		while(read.ready()) {
			str = read.readLine();
		}
		read.readLine();
		return str;
	
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
	
	public String getTree() {
		
	return nameOfTree.substring(13);
	}
	
	public String getHead() {
		return Head;
		
	}
	
	
	

	
}

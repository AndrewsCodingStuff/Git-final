
//package git;
import java.util.*;
import java.io.*;

public class Index {
	
	private HashMap<String, String> map;
	
	public static void main (String[]args) throws IOException{
		Index inx = new Index();
		inx.init();
		inx.add("foo.txt");
		inx.add("bar.txt");
		inx.add("foobar.txt");
		inx.remove("foo.txt");
	}
	
	
	public Index() {
		map = new HashMap<String, String>();
	}
	
	public void init() throws FileNotFoundException {
		File f = new File("test/index.txt");
		new File("test/objects").mkdirs();
		new File("Head");
	}
	
	public void add(String fileName) throws IOException {
		File f = new File("test/" + fileName);
		if (f.exists()) {
			Blob bob = new Blob("test/" + fileName);
//			bob.createFile();
			map.put(fileName, bob.getHashed());
			PrintWriter pw = new PrintWriter("test/index.txt");
			for (String s : map.keySet())
				pw.append(s + ": " + map.get(s) + "\n");
			pw.close();
		}
	}
	
	public void remove(String fileName) throws IOException {
		if (map.containsKey(fileName)) {
			File f = new File("test/objects/" + map.get(fileName) + ".txt");
			f.delete();
			map.remove(fileName);
			PrintWriter pw = new PrintWriter("test/index.txt");
			for (String s: map.keySet())
				pw.append(s + ": " + map.get(s) + "\n");
			pw.close();
		}
	}
}


import java.util.*;
import java.io.*;
import java.math.*;
import java.security.*;
import java.util.zip.*;

public class Blob {

	private String hashed;
	private String contents;
	private String zipped;
	private String hashedZip;
	private String original;
	
	public static void main (String[]args) throws IOException{
		Blob bob = new Blob("test/something.txt");
		
	}
	
	public Blob(String fileName) throws IOException {
		String output = "";
		
		try {
			File f = new File(fileName);
			Scanner input = new Scanner(f);
			while (input.hasNextLine())
				output += input.nextLine();
			input.close();
		}
		catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		
		contents = output;
		hashed = getSHA1(contents);
		zipped = zippedContents();
		hashedZip = getSHA1(zipped);
		original = fileName;
		createFile();
	}
	
	public String getOriginalFile() {
		return original;
	}
	
	private String getSHA1 (String s1){
		String value = s1;
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
	
	public String zippedContents() throws IOException {
	    String str = contents;
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    GZIPOutputStream gzip = new GZIPOutputStream(out);
	    gzip.write(str.getBytes());
	    gzip.close();
	    return out.toString("ISO-8859-1");
	}
	
//	public static String compress(String str) throws IOException {
//    if (str == null || str.length() == 0)
//        return str;
//    ByteArrayOutputStream out = new ByteArrayOutputStream();
//    GZIPOutputStream gzip = new GZIPOutputStream(out);
//    gzip.write(str.getBytes());
//    gzip.close();
//    return out.toString("ISO-8859-1");
//	}
	
	public void createFile() throws IOException {
		PrintWriter pw = new PrintWriter(new File("test/objects/" + hashedZip + ".txt"));
		pw.append(zipped);
		pw.close();
	}
	
	public String getHashed() {
		return hashedZip;
	}
	
	
	
}

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Tree {
	String nameOfFile = "";
	
	public Tree (ArrayList <String> strList) throws IOException, NoSuchAlgorithmException
	{
		System.out.println("tree created");
		String str = "";
		int count = 0;
		for (String g : strList) {
			if (count != 0) {
				str += "\n";
			}
			count++;
			str += g;
		}
		 // getInstance() method is called with algorithm SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest(str.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        // return the HashText
        BufferedWriter writer = new BufferedWriter(new FileWriter("test/objects/" + hashtext + ".txt"));
       String fileName =  "test/objects/" + hashtext + ".txt";
       nameOfFile = fileName;
       
        writer.write(str);
        writer.close();
    }
	

	public String getTreeName() {
		return nameOfFile;
	}
	
	
	
	//extended part, we need to delete files or
	//when u delete the original commit still has the tree that points to deleted folder. The new commit will have the tree that only points to the non deleted blobs not the tree. 
	//u go into the tree, then check if any of the blobs are the deleted file then if it isn't then you go into the next tree and go into that and you keep looking
	
	public static void main (String[]args) throws NoSuchAlgorithmException, IOException {
		ArrayList<String> t = new ArrayList<String>();
		t.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		t.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		t.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		t.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		t.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		Tree ne = new Tree(t);
		
	}
}


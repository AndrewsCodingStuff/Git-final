import static org.junit.Assert.*;
import org.junit.*;   // instead of  import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class CommitTest {

	@Test
	public void testCommit() throws NoSuchAlgorithmException, IOException {
		//fail("Not yet implemented");
		File head = new File("Head");
		boolean check = false;
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
		
		if(com3.getHead().equals(com3.getCommitName())) {
			check = true;
		}
		assertTrue(check);
		
	}

	@Test
	public void testMakeTree() throws IOException, NoSuchAlgorithmException {
		fail("Not yet implemented");
		boolean test = false;
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
		File check = new File("test/index.txt");
		if(check.length() == 0 )
			test = true;
		assertTrue(test);
	}

	@Test
	public void testWriteFile() {
		fail("Not yet implemented");
	}

}

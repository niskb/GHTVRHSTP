import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	private static String[] directories = new String[578]; // 578 IDs in TRACKS_CLEAN
	private static int count = 0; // Should be 577 in the end of the scan

	public static void main(String[] args) {
		String directoryPath = "OVERRIDE\\TRACKS_CLEAN";
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();

		// Search through directories and print in a tabbed format
		System.out.println("Run printFileName(...)");
		for (File file : files) {
			printFileName(file, 0);
		}

		System.out.println("Run scanDirectories(...)");
		// For setting up directories
		for (File file : files) {
			scanDirectories(file, 0);
		}

		System.out.println("Run printDirectories(...)");

		System.out.println("Files counted: " + count);

		// Print All Directories
		printDirectories();

		System.out.println("Print a Random File's contents with printFileContents(...)");
		// Now read a random File's contents
		int randomDirectory = (int) (Math.random() * directories.length);
		System.out.println("Random File Number: " + (randomDirectory + 1));
		printFileContents(randomDirectory);

	}

	private static void printFileName(File file, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
		if (file.isFile()) {
			System.out.println(file.getName());
		} else {
			System.out.println("Directory Name Found: " + file.getAbsolutePath());
			File[] files = file.listFiles();
			for (File fileNewLevel : files) {
				printFileName(fileNewLevel, level + 1);
			}
		}
	}

	private static void scanDirectories(File file, int level) {
		for (int i = 0; i < level; i++) {
			// Do nothing
		}
		if (file.isFile()) {
			// Do nothing
		} else {
			directories[count] = file.getAbsolutePath() + "\\TRACKCONFIG.XML";
			count++;
			File[] files = file.listFiles();
			for (File fileNewLevel : files) {
				scanDirectories(fileNewLevel, level + 1);
			}
		}
	}

	private static void printDirectories() {
		for (int i = 0; i < directories.length; i++) {
			System.out.println("Directory " + (i + 1) + " Name: " + directories[i]);
		}
	}

	private static void printFileContents(int position) {
		try {
			FileReader fr = new FileReader(directories[position]);
			BufferedReader br = new BufferedReader(fr);
			String buffer;
			String fulltext = "";
			while ((buffer = br.readLine()) != null) {
				System.out.println(buffer);
				fulltext += buffer;
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Fail");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail");
		}
	}

}

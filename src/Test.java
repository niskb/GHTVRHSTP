import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class Test {

	private static String[] directories = new String[578]; // 578 IDs in TRACKS_CLEAN
	private static int count = 0; // Should be 578 in the end of the scan

	public static void main(String[] args) {
		String directoryPath = "OVERRIDE\\TRACKS_CLEAN";
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();

		// Search through directories and print in a tabbed format
//		System.out.println("Run printFileName(...)");
//		for (File file : files) {
//			printFileName(file, 0);
//		}

		System.out.println("Run scanDirectories(...)");
		// For setting up directories
		for (File file : files) {
			scanDirectories(file, 0);
		}

//		System.out.println("Run printDirectories(...)");

		System.out.println("Files counted: " + count);

		// Print All Directories
//		printDirectories();

//		System.out.println("Print a Random File's contents with printFileContents(...)");
		// Now read a random File's contents
//		int randomDirectory = (int) (Math.random() * directories.length);
//		System.out.println("Random File Number: " + (randomDirectory + 1));
//		printFileContents(randomDirectory);

		// We need to parse each file XML

//		parser();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Negative values for slower Highway speed.");
		System.out.println("Positive values for faster Highway speed.");
		System.out.println("You probably want to input a positive value to increase the Highway speed.");
		System.out.println("Anything beyond 200 is dangerous.");
		System.out.println("Enter percentage Integer (-100 through 100): ");
		int percentageInput = scanner.nextInt();
		parseAllByPercent(percentageInput);
		scanner.close();

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

	private static void parser() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			File file = new File(directories[0]);
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();

			System.out.println("\nWe are printing out the song data for the first file in the directory list...\n"
					+ directories[0] + "\n");

			System.out.println("Root Element : " + doc.getDocumentElement().getNodeName());
			System.out.println("-----");

			// Get highway speed data
			NodeList children = doc.getChildNodes();
			Element trackElement = (Element) children.item(0);
			System.out.println("Element Name: " + trackElement.getNodeName());

			// The highway element is the second node in Track
			// If we want all of the node data, we need to look at the trackChildren length
			NodeList trackChildren = trackElement.getChildNodes();
			Element highwayElement = (Element) trackChildren.item(1);
			System.out.println("Child Element Name: " + highwayElement.getNodeName());

			// Obtain data
			String newbeginner = highwayElement.getAttribute("newbeginner");
			System.out.println("newbeginner: " + newbeginner);

			String neweasy = highwayElement.getAttribute("neweasy");
			System.out.println("newneweasy: " + neweasy);

			String newmedium = highwayElement.getAttribute("newmedium");
			System.out.println("newmedium: " + newmedium);

			String newhard = highwayElement.getAttribute("newhard");
			System.out.println("newhard: " + newhard);

			String newexpert = highwayElement.getAttribute("newexpert");
			System.out.println("newexpert: " + newexpert);

			// Modify Element
			highwayElement.setAttribute("newexpert", "2.0");

			newexpert = highwayElement.getAttribute("newexpert");
			System.out.println("Modified newexpert: " + newexpert);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.out.println("Fail");
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println("Fail");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail");
		}
	}

	private static void parseAllByPercent(int percent) {
		for (int i = 0; i < directories.length; i++) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				File file = new File(directories[0]);
				Document doc = builder.parse(file);
				doc.getDocumentElement().normalize();

				// Get Track element
				NodeList children = doc.getChildNodes();
				Element trackElement = (Element) children.item(0);

				// Get Track Highway Child element
				NodeList trackChildren = trackElement.getChildNodes();
				Element highwayElement = (Element) trackChildren.item(1);

				// Modify Element Highway Speed
				highwayElement.setAttribute("newbeginner",
						String.valueOf((percent / 100.0) + Double.valueOf(highwayElement.getAttribute("newbeginner"))));
				highwayElement.setAttribute("neweasy",
						String.valueOf((percent / 100.0) + Double.valueOf(highwayElement.getAttribute("neweasy"))));
				highwayElement.setAttribute("newmedium",
						String.valueOf((percent / 100.0) + Double.valueOf(highwayElement.getAttribute("newmedium"))));
				highwayElement.setAttribute("newhard",
						String.valueOf((percent / 100.0) + Double.valueOf(highwayElement.getAttribute("newhard"))));
				highwayElement.setAttribute("newexpert",
						String.valueOf((percent / 100.0) + Double.valueOf(highwayElement.getAttribute("newexpert"))));

				// Now recompile XML to a new File with the new data
				String newFileDirectoryPath = directories[i].replaceAll("TRACKS_CLEAN", "TRACKS");
				newFileDirectoryPath = newFileDirectoryPath.substring(newFileDirectoryPath.indexOf("OVERRIDE\\TRACKS"));
				newFileDirectoryPath = newFileDirectoryPath.replace('\\', '/');

				String filePath = newFileDirectoryPath.substring(0, newFileDirectoryPath.indexOf("/TRACKCONFIG.XML"));

				// Check if directory does not exist

				File directory = new File(filePath);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				System.out.println("File Path: " + filePath);
				System.out.println("Writing to: " + newFileDirectoryPath);

				// Now actually write the modified XML to a new File

				DOMImplementationLS ls = (DOMImplementationLS) DOMImplementationRegistry.newInstance()
						.getDOMImplementation("LS");
				LSSerializer serializer = ls.createLSSerializer();
				serializer.getDomConfig().setParameter("format-pretty-print", true);
				LSOutput output = ls.createLSOutput();

				OutputStream ostream = new FileOutputStream(newFileDirectoryPath);
				output.setByteStream(ostream);
				serializer.write(doc, output);

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				System.out.println("Fail");
			} catch (SAXException e) {
				e.printStackTrace();
				System.out.println("Fail");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Fail");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Fail");
			} catch (InstantiationException e) {
				e.printStackTrace();
				System.out.println("Fail");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				System.out.println("Fail");
			} catch (ClassCastException e) {
				e.printStackTrace();
				System.out.println("Fail");
			}
		}
	}
}

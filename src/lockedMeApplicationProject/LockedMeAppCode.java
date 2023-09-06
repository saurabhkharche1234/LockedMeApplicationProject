package lockedMeApplicationProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class LockedMeAppCode {

	public static void main(String[] args) {
		createFolder("Main");
		//display welcome message and developer details
		displayWelcomeMsg();
		handleWelcomeScreenInput();
	}
	
	public static void displayWelcomeMsg() {
		String welcomeMsg = "-------<Welcome to LockMe App>-------\n"+"               -developed by *Saurabh Kharche*";
		String functionsProvided = "This application provides below functionalities:\n" + 
									" *  Retrieve all file names in main folder\n"+
									" *  Add, delete or search file in main folder\n"+
									"[Note:Please insure the correct file name is provided for searching or deleting files.]\n\n";
		System.out.println(welcomeMsg);
		System.out.println(functionsProvided);
	}
	
	public static void displayMenu() {
		String menu = "\n\n------<Please select your option from below and press enter>------\n\n"+
						"1. Retrieve all files from main folder\n"+
						"2. Add or delete or search a file in main folder\n"+
						"3. Exit Program\n";
		System.out.println(menu);
	}
	
	public static void displayFileMenuOptions() {
		String menu = "\n\n--------<Please select your option from below and press Enter.>-------\n\n" +
	            "1. Add a file to main folder\n" +
	            "2. Delete a file from main folder\n" +
	            "3. Search for a file in main folder\n" +
	            "4. Show previous menu\n" +
	            "5. Exit Program\n";
	    System.out.println(menu);
	}
	
	public static void handleWelcomeScreenInput() {
	    boolean running = true;
	    Scanner sc = new Scanner(System.in);

	    do {
	        try {
	            displayMenu();
	            int input = sc.nextInt();

	            switch (input) {
	                case 1:
	                    displayAllFiles("main");
	                    break;
	                case 2:
	                   handleFileMenuOptions();
	                    break;
	                case 3:
	                    System.out.println("Program exited successfully");
	                    running = false;
	                    sc.close();
	                    System.exit(0);
	                    break;
	                default:
	                    System.out.println("Please retry entering correct option");
	            }
	        } catch (Exception e) {
	            System.out.println(e.getClass().getName());
	            handleWelcomeScreenInput();
	        }
	    } while (running);
	}

	private static void handleFileMenuOptions() {
	    boolean running = true;
	    Scanner sc = new Scanner(System.in);
	    do {
	        try {
	            displayFileMenuOptions();
	            createFolder("main");

	            int input = sc.nextInt();
	            switch (input) {
	                case 1:
	                    // File Add
	                    System.out.println("Enter name for File");
	                    String fileToAdd = sc.next();

	                    createFile(fileToAdd, sc);

	                    break;
	                case 2:
	                    // File/Folder delete
	                    System.out.println("Enter the filename you want to delete from main folder");
	                    String fileToDelete = sc.next();

	                    createFolder("main");
	                    List<String> filesToDelete = displayFileLocations(fileToDelete, "main");

	                    String deletionPrompt = "\nSelect index of which file to delete?"
	                            + "\n(Enter 0 if you want to delete all elements)";
	                    System.out.println(deletionPrompt);

	                    int idx = sc.nextInt();

	                    if (idx != 0) {
	                       deleteFilesRecursively(filesToDelete.get(idx - 1));
	                    } else {

	                        // If idx == 0, delete all files displayed for the name
	                        for (String path : filesToDelete) {
	                           deleteFilesRecursively(path);
	                        }
	                    }


	                    break;
	                case 3:
	                    // File/Folder Search
	                    System.out.println("Enter the name of the file to be searched from \"main\" folder");
	                    String fileName = sc.next();

	                   createFolder("main");
	                    displayFileLocations(fileName, "main");


	                    break;
	                case 4:
	                    // Go to Previous menu
	                    return;
	                case 5:
	                    // Exit
	                    System.out.println("Program exited successfully.");
	                    running = false;
	                    sc.close();
	                    System.exit(0);
	                default:
	                    System.out.println("Please select a valid option from above.");
	            }
	        } catch (Exception e) {
	            System.out.println(e.getClass().getName());
	            handleFileMenuOptions();
	        }
	    } while (running);
	}
	public static void createFolder(String folderName) {
	    File file = new File(folderName);

	    if (!file.exists()) {
	        file.mkdirs();
	    }
	}
	
	public static void displayAllFiles(String path) {
	    createFolder("main");

	    System.out.println("Displaying all files with directory structure in ascending order\n");

	    List<String> fileNames =listFileInDirectory(path, 0, new ArrayList<String>());

	    System.out.println("Displaying all files in ascending order\n");
	    Collections.sort(fileNames);

	    fileNames.stream().forEach(System.out::println);
	}

	private static List<String> listFileInDirectory(String path, int indentationCount, List<String> fileNames) {
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    List<File> fileList = Arrays.asList(files);

	    Collections.sort(fileList);

	    if (files != null && files.length > 0) {
	        for (File file: fileList) {
	            System.out.println(" ".repeat(indentationCount * 2));

	            if (file.isDirectory()) {
	                System.out.println("`--" + file.getName());

	                fileNames.add(file.getName());
	                listFileInDirectory(file.getAbsolutePath(), indentationCount + 1, fileNames);
	            } else {
	                System.out.println("|--" + file.getName());
	                fileNames.add(file.getName());
	            }
	        }
	    } else {
	        System.out.println(" ".repeat(indentationCount * 2));
	        System.out.println("|-- Empty Directory");
	    }
	    System.out.println();
	    return fileNames;
	}
	public static void createFile(String fileToAdd, Scanner sc) {
	    createFolder("main");
	    Path pathToFile = Paths.get("./main/" + fileToAdd);

	    try {
	        Files.createDirectories(pathToFile.getParent());
	        Files.createFile(pathToFile);
	        System.out.println(fileToAdd + " created successfully");
	        System.out.println("Would like to add some content to the file? (Y/N)");
	        String choice = sc.next().toLowerCase();
	        sc.nextLine();
	        if (choice.equalsIgnoreCase("Y")) {
	            System.out.println("\n\nInput content and press enter\n");
	            String content = sc.nextLine();
	            Files.write(pathToFile, content.getBytes());
	            System.out.println("\nContent written to file " + fileToAdd);
	            System.out.println("Content can be read using Notepad or Notepad++");
	        }
	    } catch (IOException e) {
	        System.out.println("Failed to create file " + fileToAdd);
	        System.out.println(e.getMessage());
	    }
	}
	public static List<String> displayFileLocations(String fileName, String path) {
	    List<String> fileNames = new ArrayList<>();
	    searchFileReursively(path, fileName, fileNames);

	    if (fileNames.isEmpty()) {
	        System.out.println("\n\n--<Could not find any file with the given file name:: " + fileName + "\n\n");
	    } else {
	        System.out.println("\n\nFound file at below location(s):");

	        List<String> files = IntStream.range(0, fileNames.size())
	                .mapToObj(index -> (index + 1) + ": " + fileNames.get(index)).collect(Collectors.toList());

	        files.forEach(System.out::println);
	    }
	    
	    return fileNames;
	}

		private static void searchFileReursively(String path, String fileName, List<String> fileNames) {

	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    List<File> fileList = Arrays.asList(files);

	    if (files != null && files.length > 0) {
	        for (File file: fileList) {
	            if (file.getName().startsWith(fileName)) {
	                fileNames.add(file.getAbsolutePath());
	            }

	            if (file.isDirectory()) {
	                searchFileReursively(file.getAbsolutePath(), fileName, fileNames);
	            }
	        }
	    }
	}
		public static void deleteFilesRecursively(String path) {
		    File currFile = new File(path);
		    File[] files = currFile.listFiles();

		    if (files != null && files.length > 0) {
		        for (File file: files) {
		            String fileName = file.getName() + " at " + file.getParent();
		            if (file.isDirectory()) {
		                deleteFilesRecursively(file.getAbsolutePath());
		            }

		            if (file.delete()) {
		                System.out.println(fileName + " deleted successfully");
		            } else {
		                System.out.println("Failed to delete " + fileName);
		            }
		        }
		    }

		    String currFileName = currFile.getName() + " at " + currFile.getParent();
		    if (currFile.delete()) {
		        System.out.println(currFileName + " deleted successfully");
		    } else {
		        System.out.println("Failed to delete " + currFileName);
		    }
		}


}

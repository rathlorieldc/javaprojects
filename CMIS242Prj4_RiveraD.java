/*
 * 
 * Deysha Rivera - CMIS 242
 * Program Completion Date: July 13, 2021
 * 
 * Program purpose: design and implement a media rental system. The program prompts a user to enter a directory and reads data from a file PRJ4rentals.
 * The program parses data from the file and adds each row of media items to an arraylist. The program organizes the files based on their type as indicated in
 * the file. The program implements class to load, data from the file, display the media data, and find, rent, and return media objects. The program uses
 * menu-driven options from the console to prompt the user to enter values and control the program. The program implements exception handling to
 * prevent the program from crashing when incorrect values are entered.
 */

package a4;

import java.util.Calendar;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class CMIS242Prj4_RiveraD {
	
public abstract static class Media {
	
		// private attributes
		
		private int id;
		private String title;
		private int year;
		private boolean isRented;
		
		// constructor to pass through array from file and assign to class variables
		
		public Media(String[] array) {
			
			this.id = Integer.parseInt(array[0].trim());
			this.isRented = Boolean.parseBoolean(array[1].trim());
			this.title = array[3].trim();
			this.year = Integer.parseInt(array[4].trim());
			
		} // end constructor
		
		// overloading constructor prompts user to enter values and assigns them to local variables
		
		public Media(Scanner scan, int id) {
			
			this.id = id;
			
			while (true) {
				
				try {
					
					// prompt user to enter title
					
					System.out.print("\nPlease enter a title: ");
					title = scan.nextLine();
					
					// check if illegal title
					
					checkIfTitleException(title);
					
					break;
					
					} catch (IllegalTitleException exception) {
						
					// display message if illegal title
					
					System.out.println("\n" + exception.toString() + " Please try again and enter a valid name.");
					
					}
				}
			
			while (true) {
				
			try {
				
				// call method to prompt user to enter year (will be overriden in child classes)
					
				System.out.print(yearPrompt());
				
				year = Integer.parseInt(scan.nextLine());
				
				// check if year is valid
				
				checkValidYearException(year);
				
				break;
				
				} catch (NumberFormatException exception) {
				
					// display message if a number is not entered
				
					System.out.println("\n" + exception.toString() + ". Invalid year. Please enter a valid year.");
				
				} catch (IllegalArgumentException exception) {
					
					// display message if illegal argument
					
					System.out.println(exception.toString());
				}
			}
			
		} // end overloading Media constructor
		
		// get methods
		
		public int getMediaID() {
			
			return id;
		}
		
		public String getMediaTitle() {
			
			return title;
		}
		
		public int getYear() {
			
			return year;
		}
		
		public boolean getIsRented() {
			
			return isRented;
		}		
		
		// set methods
		
		public void setTitle(String title) {
			
			this.title = title;
		}
		
		public void setYear(int year) {
			
			this.year = year;
		}
		
		public void setIsRented(boolean rented) {
			
			this.isRented = rented;
		}
		
		// method to return rental fee
		
		public double calculateRentalFee() {
			
			return 3.50;
		}
		
		// method to throw exception if title is not null, blank, or empty
		
		protected void checkIfTitleException(String name) {
			
			if (name == null || name.isBlank() || name.isEmpty()) {
				
				throw new IllegalTitleException(name);
				
			}
		} // end checkIfTitleException method
		
		// method to throw exception if year is negative
		
		protected void checkValidYearException(int year) throws IllegalArgumentException {
			
			if (year < 0) {
				
				throw new IllegalArgumentException("Year cannot be less than zero.");
			}
		} // end checkValidYearException method
		
		// method to prompt user to enter publication year
		
		public String yearPrompt() {
			
			return "\nPlease enter publication year: ";
		} // end yearPrompt method
		
		// method to write data to file
		
		public String toFile() {
			
			return String.format("%s, %s, %s, %s, %.2f", getMediaID(), getIsRented(), getMediaTitle(), getYear(), calculateRentalFee());
		} // end toFile method

	} // end media class
	
	public static class EBook extends Media {
		
		private int numChapters;
		
		// constructor passes through array from file and assigns values to class variables
		
		public EBook(String[] array) {
			
			super(array);
			this.numChapters = Integer.parseInt(array[5].trim()); // sixth column
		} // end constructor
		
		// overloading constructor passes through scanner and prompts user to enter values and assigns them to class variables
		
		public EBook(Scanner scan, int id) {
			
			super(scan, id);
			
			while (true) {
				
			try {
					
				System.out.print("\nPlease enter the number of chapters: ");
				this.numChapters = Integer.parseInt(scan.nextLine());
				
				break;
				
				} catch (NumberFormatException exception) {
				
				System.out.println("\n" + exception.toString() + ". Invalid number of chapters. Please enter an integer value.");
				
				}			
			}			
			
		} // end overloading Ebook constructor
		
		// get methods
		
		public int getNumChapters() {
			
			return numChapters;
		}
		
		// set methods
		
		public void setNumChapters(int chapters) {
			
			this.numChapters = chapters;
		}
		
		@Override
		
		// override method to calculate rental fee
		
		public double calculateRentalFee() {
			
			double fee = numChapters * 0.10;
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			
			// add $1 if ebook was published this year
			
			if (this.getYear() == currentYear) {
				
				fee += 1; // add $1 fee
			}
			
			return fee;
		} // end calculateRentalFee method
		
		@Override
		
		// override method to write data to console
		
		public String toString() {
			
			return "EBook [id = " + getMediaID() + ", title = " + getMediaTitle() + ", year = " 
			+ getYear() + ", chapters = " + numChapters + ", rented = " + getIsRented() + "]";
			
		} // end toString method
		
		@Override
		
		// override method to write data to file
		
		public String toFile() {
			
			return String.format("%s, %s, E, %s, %s, %s, %.2f", getMediaID(), getIsRented(), getMediaTitle(), getYear(), numChapters, calculateRentalFee());
		} // end toFile method
		
	} // end eBook class
	
	public static class MovieDVD extends Media {
		
		// private attributes
		
		private double megabytes;
		
		// constructor passes through array from file and assigns class variables
		
		public MovieDVD(String[] array) {
			
			super(array);
			this.megabytes = Double.parseDouble(array[5].trim()); // sixth column
		} // end MovieDVD constructor
		
		// overloading constructor passes through scanner and prompt user to enter values and assigns them to class variables
		
		public MovieDVD(Scanner scan, int id) {
			
			// call super constructor
			
			super(scan, id);
			
			while (true) {
				
			try {
				
				// promp tuser to enter movie size
					
				System.out.print("\nPlease enter the size in MegaBytes: ");
				this.megabytes = Double.parseDouble(scan.nextLine());
				
				break;
				
				} catch (NumberFormatException exception) {
					
				// display message if number not entered
				
				System.out.println("\n" + exception.toString() + ". Invalid data type. Please enter a double value.");
				
				}			
			}
			
		} // end overloading MovieDVD constructor
		
		// get methods
		
		public double getMegabytes() {
			
			return megabytes;
		}
		
		// set methods
		
		public void setMegabytes(double megabytes) {
			
			this.megabytes = megabytes;
		}
		
		// inherits calculate rental fee; no override calculation
		
		@Override
		
		// method to prompt user to enter year data
		
		public String yearPrompt() {
			
			return "\nPlease enter release year: ";
		} // end yearPrompt method
		
		@Override
		
		// method to write data to console
		
		public String toString() {
			
			return "MovieDVD [id = " + getMediaID() + ", title = " + getMediaTitle() + ", year = " 
			+ getYear() + ", size = " + megabytes + "MB, rented = " + getIsRented() + "]";
		} // end toString method
		
		@Override
		
		// method to write data to file
		
		public String toFile() {
			
			return String.format("%s, %s, D, %s, %s, %.0f, %.2f", getMediaID(), getIsRented(), getMediaTitle(), getYear(), megabytes, calculateRentalFee());
		} // end toFile method

	} // end MovieDVD class
	
	public static class MusicCD extends Media {
		
		// private attributes
		
		private int minutes;
		
		// constructor to pass through array parsed from file and assign to class variables
		
		public MusicCD(String[] array) {
			
			super(array);
			this.minutes = Integer.parseInt(array[5].trim()); // sixth column
		} // end MusicCD array constructor
		
		// overloading constructor passes through scanner to prompt user to enter values and assign them to class variables
		
		public MusicCD(Scanner scan, int id) {
			
			// call super method
			
			super(scan, id);
			
			while (true) {
				
			try {
				
				// prompt user to enter album length
					
				System.out.print("\nPlease enter the album length in minutes: ");
				minutes = Integer.parseInt(scan.nextLine());
				
				break;
				
				} catch (NumberFormatException exception) {
					
				// message if user does not enter integer values
				
				System.out.println("\n" + exception.toString() + ". Invalid length. Please enter an integer value.");
				
				}			
			}			
			
		} // end MusicCD overloading constructor
		
		// get methods
		
		public int getMinutes() {
			
			return minutes;
		} 
		
		// set methods
		
		public void setMinutes(int minutes) {
			
			this.minutes = minutes;
		} 
		
		@Override
		public double calculateRentalFee() {
			
			double fee = minutes * 0.02;
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			
			if (this.getYear() == currentYear) {
				
				fee += 1; // add $1 fee
				
			}
			
			return fee;
		} // end calculateRentalFee method
		
		@Override
		
		// override method to prompt user to enter year
		
		public String yearPrompt() {
			
			return "\nPlease enter release year: ";
		} // end yearPrompt method
		
		@Override
		
		// method to display data to console
		
		public String toString() {
			
			return "MusicCD [id = " + getMediaID() + ", title = " + getMediaTitle() + ", year = " 
			+ getYear() + ", length = " + minutes + " min, rented = " + getIsRented() + "]";
		} // end override toString method
		
		@Override
		
		// method to write data to file
		
		public String toFile() {
			
			return String.format("%s, %s, C, %s, %s, %s, %.2f", getMediaID(), getIsRented(), getMediaTitle(), getYear(), minutes, calculateRentalFee());
		} // end override toFile method

	} // end MusicCD class
	
	public static class IllegalTitleException extends IllegalArgumentException {
		
		// private attribute
		
			private String message;
			
			// constructor
			
			public IllegalTitleException (String name) {
				
				// logic to handle if name is valid
				
				if (name == null) {
					
					message = "Title cannot be null.";
					
				} else if (name.isBlank()) {
					
					message = "Title cannot be blank.";
					
				} else if (name.isEmpty()) {
					
					message = "Title cannot be empty.";
				}
			}
			
			// override toString method to display message
			
			@Override
			
			public String toString() {
				
				return this.getClass().getSimpleName() + " : " + message;
			}
		
	} // end IllegalTitleException class
	
	public static class InvalidIDException extends IllegalArgumentException {
		
		private String message;
		
		// constructor
		
		public InvalidIDException(String id) {
			
			// logic to check if id length is 5
			
			if (id.length() != 5) {
				
				// message if not
				
				message = "ID must have a length of 5 integers.";
			}	
			
		}
		
		@Override
		
		// override to string method to display class data and message
		
		public String toString() {
			
			return this.getClass().getSimpleName() + " : " + message;
		} // end toString method
		
	} // end InvalidIdException class
	
	public static class Manager {
		
		// private media attributes
		
		private ArrayList<Media> mediaList = new ArrayList<Media>();
		
		// private file attributes
	
		private File file = null;
		private PrintWriter output;
		
		// constructor 
		
		public Manager() {
			
			mediaList = new ArrayList<Media>();
		}
		
		// overloading constructor passes through file as argument
		
		public Manager(File file) {
			
			this.file = file;
		} // end overloading constructor 
		
		
		/*
		 * method to read data from file. prompts user to enter the directory displays message if no directory is given. method creates a new file
		 * wrapped in a scanner (data) and displays a message if file is not found. the method parses data from the file, checks the media type and calls
		 * the appropriate constructor, passes through an array and creates an instance of that media type. the method then adds the instance to the arraylist.
		 */
		
		public void readData(Scanner data) throws Exception {
			
			// local attributes
			
			Media media = null;	
		
			try {
				
				// wrap file in scanner to read data
				
				data = new Scanner(new BufferedReader(new FileReader(file)));
				
				
			} catch (FileNotFoundException exception) {
				
				// display message if file not found
				
				System.out.println("\nError. File not found. Program ending.");
				System.exit(0);				
			}			
			
			
			System.out.println("\n-------------------- Reading data from file " + file + "-------------------");
			
			// parse each line in the file and add it to an array
			
			while (data.hasNextLine()) {
				
				String line = data.nextLine().trim();
				
				String[] fields = line.split(",");
				
				if (line.isBlank() || line.isEmpty()) {
					
					// skip line if blank or empty
					
					continue;
				}
				
				// variable mediaType for second column in the array
				
				String mediaType = fields[2].trim();
				
				// create instance based on media type and pass through array
				
				if (mediaType.equalsIgnoreCase("E")) {
					
					media = new EBook(fields);
						
				} else
				
				if (mediaType.equalsIgnoreCase("C")) {
					
					media = new MusicCD(fields);
					
				} else
				
				if (mediaType.equalsIgnoreCase("D")) {
					
					media = new MovieDVD(fields);
				}
				
				// add instance to arraylist and display data
				
				mediaList.add(media);
				System.out.println("\nAdded: " + media.toString());			
				
			} //end while loop
		}// end readData method
		
		/*
		 * method to write data to file. the method creates a new printwriter and passes through the file variable. message is displayed if the file is not
		 * found. methods loops over the array and writes each instance of media to a new line in the file using the toFile override method.
		 */
		
		public void writeData() {
			
			try {
				
				// create new PrintWriter to write data to file
				
				output = new PrintWriter(new FileWriter(file));
				
				
			} catch (IOException exception) {
				
				// display message if file not found
				
				System.out.println("\nFile not found. Program ending.");
				System.exit(0);
			}
			
			System.out.println("\n-------------------- Writing data to file " + file + " -------------------");
			
			for (int index = 0; index < mediaList.size(); index++) { 
				
				Media media = mediaList.get(index); // get media instance from the list for each index
				
				output.println(media.toFile()); // print to file
				
			}
			
			output.flush(); // flush data to file
			output.close(); // close the file
			
		} // end writeData method
		
		/*
		 * method to display menu selection for media type. the method prompts the user to make a selection, and calls the method to display the media
		 * type based on the selection. method displays a message if the selection is invalid.
		 */
		
		public void displayMediaList(Scanner scan) {
			
			// local attributes
			
			int selection = 0;
			
			while (true) {
			
				try {
					
				// prompt user to make selection	
				
				System.out.print("\nPlease select media type to display: (1) EBook, (2) Movie DVD , (3) Music CD, (4) Display all media: ");
				selection = Integer.parseInt(scan.nextLine());
				
				break;
				
				} catch (NumberFormatException exception) {
					
					// print message if integer was not entered
					
					System.out.println("\n" + exception.toString() + ". Invalid selection. Please try again.");
				}
			}
			
			// call methods to display media type based on selection
			
			if (selection == 1) {
				
				displayEbookList();
				
			} else if (selection == 2) {
				
				displayMovieList();
				
			} else if (selection == 3) {
				
				displayMusicList();
				
			} else if (selection == 4) {
			
				displayAllMedia();
				
			} else {
				
				// print message to console if selection is invalid
				
				System.out.println("\nYou did not enter a valid selection. Please try again!");
				
			}
		} // end displayMediaList method
		
		/*
		 * method to display all media objects. prints a default statement if the list is empty. if not, 
		 * the method loops over the entire arraylist and prints each instance of media. calls either the toString method or override toString
		 * method on each instance to display data. 
		 */
		
		public void displayAllMedia() {
			
			if (mediaList.size() == 0) { // check if list is empty
				
				System.out.println("\nList is empty."); // print error message
			}
		
			// loop over the arraylist to display all media instances
		
			for (int index = 0; index < mediaList.size(); index++) { 
				
				Media media = mediaList.get(index); // get media instance from the list for each index
				
				System.out.println(); // print an empty line
				
				System.out.println(media.toString()); // display data if found
				
			}
		} //end displayAllMedia method
		
		/*
		 * method to display all EBook objects. the method checks if the list is empty. if not, it loops over the arraylist and prints
		 * each instance of EBook. the instance is skipped if it is not an EBook. calls the override toString method to display EBook data.
		 */
			
		public void displayEbookList() {
			
			if (mediaList.size() == 0) { // check if list is empty
				
				System.out.println("\nList is empty."); // print error message
			}
			
			// loop over the array list to display all ebook instances
			
			for (int index = 0; index < mediaList.size(); index++) { 
				
				Media media = mediaList.get(index); // get ebook instance from the list for each index
				
				// skip instance if not EBook
				
				if (!(media instanceof EBook)) {
					
					continue;
				}
				
				System.out.println(); // print an empty line
				
				System.out.println(media.toString()); // display data if found				
			}
			
		} // end displayEbookList method
		
		/*
		 * method to display all MovieDVD objects. the method checks if the list is empty. if not, it loops over the arraylist and prints
		 * each instance of MovieDVD. the instance is skipped if it is not a MovieDVD. calls the override toString method to display MovieDVD data.
		 */
		
		public void displayMovieList() {
			
			if (mediaList.size() == 0) { // check if list is empty
				
				System.out.println("\nList is empty."); // print error message
			}
			
			// loop over the array list to display all moviedvd instances
			
			for (int index = 0; index < mediaList.size(); index++) { 
				
				Media media = mediaList.get(index); // get moviedvd instance from the list for each index
				
				// skip object if not MovieDVD
				
				if (!(media instanceof MovieDVD)) {
					
					continue;
				}
				
				System.out.println(); // print an empty line
				
				System.out.println(media.toString()); // display data if found
				
			}
		} // end displayMovieList method
		
		/*
		 * method to display all MusicCD objects. the method checks if the list is empty. if not, it loops over the arraylist and prints
		 * each instance of MusicCD. the instance is skipped if it is not a MusicCD. calls the override toString method to display MusicCD data.
		 */
		
		public void displayMusicList() {
			
			if (mediaList.size() == 0) { // check if list is empty
				
				System.out.println("\nList is empty."); // print error message
			}
			
			// loop over the array list to display all music instances
			
			for (int index = 0; index < mediaList.size(); index++) { 
				
				Media media = mediaList.get(index); // get music instance from the list for each index
				
				// skip object if not MusicCD
				
				if (!(media instanceof MusicCD)) {
					
					continue;
				}
				
				System.out.println(); // print an empty line
				
				System.out.println(media.toString()); // display data if found
				
			}
		} // end displayMusicList method
		
		/*
		 * method to add media objects. the method prompts the user to choose if they want to enter an EBook, DVD, or CD. if they do not make a
		 * valid selection, the method handles the exception. the method calls the constructor for each media type depending on the selection and
		 * instantiates the media object. the method adds the media object to the arraylist, calls the toString method or override toString method
		 * to display data, and updates the rentals file.
		 */
		
		public void addMedia(Scanner scan) {
			
			Media media = null;
			
			// initialize local variable
			int selection = 0;
			int id = 0;
			
			while (true) {
			
				try {
					
				// prompt user to make selection
				
				System.out.print("\nPlease enter a selection for media item to add: (1) EBook, (2) Movie DVD , (3) Music CD: ");
				selection = Integer.parseInt(scan.nextLine());
				
				break;
				
				// catch exception if invalid selection
				
				} catch (NumberFormatException exception) {
					
					System.out.println("\n" + exception.toString() + ". Invalid selection. Please try again.");
				}
			}
			
			while (true) {
				
				try {
					
					// prompt user to enter id
				
					System.out.print("\nPlease enter an ID with a length of five integers: ");
					String userInput = scan.nextLine();
					
					id = Integer.parseInt(userInput);
					
					// calls method to check for invalididexception
				
					checkValidIDException(userInput);
					
					if (checkDuplicateID(id) == true) {
						
						continue;
					}
				
					break;
				
					} catch (NumberFormatException exception) {
						
						// display message if a number is not entered
					
						System.out.println("\n" + exception.toString() + ". Invalid ID. Please enter an integer value.");
					
					} catch (InvalidIDException exception2) {
						
						// display message if id not valid
						
						System.out.println("\n" + exception2.toString() + " Please enter a valid ID.");
						
					} catch (IllegalArgumentException exception3) {
						
						System.out.println(exception3.toString());
					}				
			}
		
			
			if (selection == 1) {
				
				// call EBook constructor to instantiate media object
				
				media = new EBook(scan, id);
				
			} else if (selection == 2) {
				
				// call MovieDVD constructor to instantiate media object
				
				media = new MovieDVD(scan, id);
				
			} else if (selection == 3) {
				
				// call MusicCD constructor to instantiate media object
				
				media = new MusicCD(scan, id);
				
			} else {
				
				System.out.println("\nYou did not enter a valid selection. Please try again!");
			}
			
			// check if media is null, if not, display data, add to arraylist and write to file
			
			if (media != null) {
				
				System.out.println("\nAdded: " + media.toString());
				
				mediaList.add(media);			
				writeData();				
			}
				
		} // end addMedia method
		
		/*
		 * method to find media objects. the method prompts the user for a title and loops over the arraylist to print all objects with the
		 * provided title. if found, the toString method is called to display data. a message is displayed to the user if the title was not found.
		 */
		
		public void findMedia(Scanner scan) {
			
			//local variables
			
			boolean found = false;
			String title = null;
			
			// prompt user for media title
			
			while (true) {
				
				System.out.print("\nPlease enter the title of the media object you want to find: ");
				title = scan.nextLine();
				
				
				if (title.isBlank() || title.isEmpty()) {
					
					System.out.println("\nYou must enter a title!");
					
				} else {
					
					break;
				}
			}
			
			// loop over the array and display the data for each instance of media with matching title
			
			for (Media media : mediaList) {
				
				if (!media.getMediaTitle().equalsIgnoreCase(title)) {
					
					// skip object if title does not match
					
					continue;
					
				} else {
					
					// found = true if title found
					
					found = true;
					System.out.println(); //print an empty line
					System.out.println(media.toString());		
				} 
			}
			
			// message if not found
			
			if (found == false) {
				
				System.out.println("\nTitle not found.");
			}
			
		} // end findMedia method
		
		/*
		 * method to rent media object. the method prompts the user to enter the item ID and loops over the array list to find the object. if found, 
		 * the method displays data about the rented object to the user and sets the boolean value for isRented to true. the method then updates the rentals file.
		 * displays a message to the user if the object was not found.
		 */
		
		public void rentMedia(Scanner scan) {
			
			// local variable to find object with default value false
			
			boolean found = false;
			int id = 0;
			
			while (true) {
				
				try {
					
				// prompt user to enter id
				
				System.out.print("\nPlease enter an ID with a length of five integers: ");
				String userInput = scan.nextLine();
				id = Integer.parseInt(userInput);

				// calls method to check for invalididexception
				
				checkValidIDException(userInput);
			
				break;
				
				} catch (NumberFormatException exception) {
					
					// display message if a number is not entered
				
					System.out.println("\n" + exception.toString() + ". Invalid ID. Please enter an integer value.");
				
				} catch (InvalidIDException exception2) {
					
					System.out.println("\n" + exception2.toString());
					
				} catch (IllegalArgumentException exception3) {
					
					System.out.println(exception3.toString());
				}
				
			}
			
			// loop over array
		
			for (Media media : mediaList) {
				
				// skip if id does not match
				
				if (media.getMediaID() != id) {
					
					continue;
					
				} else {
					
					// check if item is already rented
					
					if (media.getIsRented() == true) {
						
						System.out.println("\nThis item is already rented.");
						
						return;
						
					}
					
					// set local variable to true and display data. set isRented value to true.	
			
					found = true;				
					
					
					System.out.printf("\nRented item: Title = %s. Rental fee = $%.2f", media.getMediaTitle(), media.calculateRentalFee());
					media.setIsRented(true);
					
					System.out.println();
				}
				
				
			}
			
			// update file if instance was found
			
			if (found == true) {
				
				writeData();
			}
			
			// message if instance was not found
			
			if (found == false) {
				
				System.out.println("\nItem not found. Please check the ID.");
			}	           				
			
			
		} // end rentMedia method
		
		/*
		 * method to return media object. the method prompts the user to enter the item ID and loops over the array list to find the object. if found, 
		 * the method checks if the item was actually rented. if rented, the method displays data about the rented object to the user and sets the 
		 * boolean value for isRented to true. the method then updates the rentals file. displays a message to the user if the object was not found.
		 */
		
		public void returnMedia(Scanner scan) {
			
			// local variables
			
			boolean found = false;
			int id = 0;
			
			while (true) {
				
				try {
					
				// prompt user to enter id
				
				System.out.print("\nPlease enter an ID with a length of five integers: ");
				String userInput = scan.nextLine();
				id = Integer.parseInt(userInput);

				// calls method to check for invalididexception
				
				checkValidIDException(userInput);
			
				break;
				
				} catch (NumberFormatException exception) {
					
					// display message if a number is not entered
				
					System.out.println("\n" + exception.toString() + ". Invalid ID. Please enter an integer value.");
				
				} catch (InvalidIDException exception2) {
					
					System.out.println("\n" + exception2.toString());
					
				} catch (IllegalArgumentException exception3) {
					
					System.out.println(exception3.toString());
				}
				
			}
					
			// loop over array
		
			for (Media media : mediaList) {
				
				// skip if id does not match
				
				if (media.getMediaID() != id) {
					
					continue;
					
				// check if media was actually rented
					
				} else if (media.getIsRented() != true) {
					
					// display message and return to menu if item was not rented
					
					System.out.println("\nItem " + media.toString() + " was not rented.");
					
					return;
					
				} else {
					
					// set local variable found to true
					
					found = true;
					
					// call method to set isRented value to false and display media info
					
					media.setIsRented(false);							
						
					System.out.println("\nItem found! Returning item: " + media.toString());
					
					System.out.println();
					
				}
				
			}
			
			// update file if instance was found
			
			if (found == true) {
				
				writeData();
			}
			
			// message if instance was not found
			
			if (found == false) {
				
				System.out.println("\nItem not found. Please check the ID.");
			} 	
		
		} // end returnMedia method
		
		// method to check for valid id length
		
		private void checkValidIDException(String userInput) {
			
			if (userInput.length() != 5) {
				
				throw new InvalidIDException(userInput);
			}
			
			int id = Integer.parseInt(userInput);
			
			if (id < 0) {
				
				throw new IllegalArgumentException("\nID cannot be less than zero!");
			}
			
		} // end checkValidIDException method
		
		// method to check if id already exists in the array loop
		
		private boolean checkDuplicateID(int id) {
			
			// loop over array
			
			for (Media idCheck : mediaList) {
				
				// skip if id does not match
				
				if (idCheck.getMediaID() == id) {
					
					System.out.println("\nThis ID already exists. You must enter a unique ID.");
					
					return true;
					
				} 				
			}
			
			return false;
		} // end checkDuplicateID method
	
	} // end Manager class
	
	public static class MediaRentalSystem {
		
		private File file = new File("PRJ4Rentals.txt");
		
		Manager manager = new Manager(file); // instantiate file manager and pass through the file
		
		// display menu to the user
		
		public void displayMenu() {
			
			System.out.println("\n   MENU   ");
			System.out.println("1. Load media data from file(PRJ4Rentals.txt).");
			System.out.println("2: Display media data.");
			System.out.println("3: Add media object.");
			System.out.println("4: Find media object.");
			System.out.println("5: Rent media object.");
			System.out.println("6: Return media object.");
			System.out.println("9: Exit program.");
			
		} // end displayMenu method
		
		// method to process choice
		
		public void processChoice(int choice, Scanner scan) throws Exception {
			
			
			switch (choice) {
			
			case 1 :		manager.readData(scan);
								break;
							
			case 2 :		manager.displayMediaList(scan);
								break;
								
			case 3 : 		manager.addMedia(scan);
								break;
								
			case 4 :		manager.findMedia(scan);
								break;
								
			case 5 :		manager.rentMedia(scan);
								break;
								
			case 6 :		manager.returnMedia(scan);
								break;
								
			case 9 :		System.out.println("\nThank you for using the program. Goodbye!");
								break;
								
			default:		System.out.println("\nInvalid choice.");
			
			}
			
		} // end process choice method		
		
	} //end MediaRentalSystem class

	public static void main(String[] args) throws Exception {
		
		MediaRentalSystem run = new MediaRentalSystem(); // instantiate RunFileManager
		
		System.out.println();  //print an empty line
		
		Scanner scanin = new Scanner(System.in); // create scanner
		int selection = 0; // initialize selection variable
		
		do { // do until user chooses to exit the program
			
			run.displayMenu(); // display the menu
			
			System.out.print("\nPlease make a selection : ");
			selection = Integer.parseInt(scanin.nextLine()); // scan the selection
			
			run.processChoice(selection, scanin); // run method to process menu selection
			
		} while (selection != 9); // end if user chooses 9
		
		scanin.close(); // close the scanner

	} // end main method


} // end CMIS242Prj4_RiveraD class

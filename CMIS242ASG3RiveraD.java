/*
 * Deysha Rivera - CMIS 242
 * Program Completion Date: June 26, 2021
 * 
 * Program Purpose: Design and implement a temperature and distance converter program. The program will allow a user to input a fahrenheit value and return the celsius value, or input
 * a miles value and return the kilometers value. If the instance has no input value, it will display a message if there is no input. The program implements a GUIConverter class
 * using JFrame and JPanel to allow the user to interact with buttons to select converters and exit the program, and creates input boxes to enter values. The input box has an 
 * "Ok" button and a "Cancel" button; once the user clicks okay, a pop up message shows the conversion results.
 * 
 */

package a3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CMIS242ASG3RiveraD {
	
	private abstract static class Converter {
		
		// private attributes
		
		private double input;		
		
		// default constructor
		
		public Converter() {			
			
			// default values
			
			input = Double.NaN;
		} // end default constructor
		
		// get method
		
		public double getInput() {
			
			return input;
		}
		
		// set method
		
		public void setInput(double input) {
			
			this.input = input;
		}
		
		// abstract String methods must be implemented by child classes
		
		public abstract String convert();
		
		public abstract String message();
		
		public abstract String title();
		
	} // end converter class
	
	public static class TemperatureConverter extends Converter {
	
		// default constructor
		
		public TemperatureConverter() {
			
			super();
		} // end default constructor
		
		/*
		 *  override convert method has a formula to calculate celsius based on user input. the method returns a string that will be displayed in a new window using
		 *  JOptionPane.showInputDialog.
		 */
		
		public String convert() {			
			
			double celsius = ((getInput() - 32) * 5)/9;
			
			return String.format("%.2f degrees Fahrenheit equals %.2f degrees Celsius", getInput(), celsius);
						
		} // end override convert method
		
		// override methods to display window title and message
		
		public String message() {
			
			return "Enter Temperature in Fahrenheit: ";
		}
		
		public String title() {
			
			return "Temperature Converter";
		}
		
	} // end TemperatureConverter class
	
	public static class DistanceConverter extends Converter {
		
		// default constructor
		
		public DistanceConverter() {
			
			super();
		} // end default constructor
		
		/*
		 *  override convert method contains a formula to calculate kilometers based on user input. the method returns a string
		 *  that will be displayed in a new window using JOptionPane.showInputDialog.
		 */
		
		public String convert() {
			
			double kilometers = getInput() * 1.609;
			
			return String.format("%.2f miles equals %.2f kilometers", getInput(), kilometers);
							
		} // end override convert method
		
		// overriding methods to display window title and message
		
		public String message() {
			
			return "Enter Distance in Miles: ";
		}
		
		public String title() {
			
			return "Distance Converter";
		}
		
	} // end DistanceConverter class
	
	private static class GUIConverter extends JPanel implements ActionListener {
		
		// private attributes
		
		private JFrame frame;
		private JButton distConverterButton;
		private JButton tempConverterButton;		
		
		// default constructor
		
		public GUIConverter() {
			
			super(new BorderLayout()); // call the JPanel super BorderLayout
			
			// create the frame
			
			frame = new JFrame("Welcome to Converter");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program will end if window is closed
			
			// create and add buttons
			
			distConverterButton = new JButton("Distance Converter");
			frame.add(distConverterButton, BorderLayout.WEST);			
			
			tempConverterButton = new JButton("Temperature Converter");
			frame.add(tempConverterButton, BorderLayout.EAST);
			
			JButton exitButton = new JButton("Exit");
			frame.add(exitButton, BorderLayout.SOUTH);
			
			// add action listeners
						
			distConverterButton.addActionListener(this);
			tempConverterButton.addActionListener(this);
			
			ExitHandler exitListener = new ExitHandler();
			exitButton.addActionListener(exitListener);
			
			// sizing main frame
			
			frame.setSize(350, 150); // sets size of the window
			frame.setLocation(500, 500); // sets x,y location 
			frame.setVisible(true);	
			
			
		} // end default constructor
		
		/*
		 * method to perform action of instantiating converter based on the button the user clicks. depending on which button 
		 * is clicked (Convert Distance or Convert Temperature, a new converter class is instantiated, the windows are populated with their respective
		 * titles and messages, and the conversion is done using the appropriate method from the chosen Converter class.
		 */
		
		public void actionPerformed(ActionEvent e) {
			
			// Converter class variable to be instantiated depending on user click
			
			Converter converter;
			
			if (e.getSource() == distConverterButton) { // if convert distance button is clicked
				
				converter = new DistanceConverter(); //	bind DistanceConverter class	
				
			} else  {
			
				converter = new TemperatureConverter(); // bind TemperatureConverter class
			 
			}
			
			// window will displays after converter button is clicked, and will populate with information based on which converter was chosen
			
			String userInput = JOptionPane.showInputDialog(frame, converter.message(), converter.title(), JOptionPane.QUESTION_MESSAGE);
			
			if (userInput.isEmpty()) {
				
				// message to display if "ok" button is clicked, but nothing is entered
				
				JOptionPane.showMessageDialog(frame, "You did not enter any data!", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			// call set method to convert and set double value for input
			
			converter.setInput(Double.parseDouble(userInput));
			
			// window will display after user input is submitted, it calls the converter method on the chosen instance and outputs the result
			
			JOptionPane.showMessageDialog(frame, converter.convert(), "Conversion Result", JOptionPane.INFORMATION_MESSAGE);		
			
		} // end actionPerformed()
		
		
	} // end GUIConverter
	
	private static class ExitHandler implements ActionListener {
		
		// method to exit program when the exit button is clicked is performed
			
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Thank you for using the Converter Program! Goodbye."); //print exit message to console
			System.exit(0); // exit program
		}
	} // end ExitHandler class

	public static void main(String[] args) {
		
		// run the program
		
		new GUIConverter();

	}

} // end program

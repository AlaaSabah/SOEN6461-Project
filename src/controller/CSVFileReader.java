package controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import model.StockSubject;

public class CSVFileReader implements DataReader{


	public CSVFileReader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List readAll() {
		
		File selectedFile = chooseFile();
		if(selectedFile == null || !selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length()-4).equals(".csv")){
	    	JOptionPane.showMessageDialog(null, "Wrong file type. Please select csv file !!","ERROR", JOptionPane.ERROR_MESSAGE);
	    	return null;
	    }
		
		try {
			
		FileReader reader = new FileReader(selectedFile.getAbsolutePath());
		CSVReader r = new CSVReader(reader);
			return r.readAll();
		} 
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to read the file. Please try again !","ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	private File chooseFile(){
		
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			
		    selectedFile = fileChooser.getSelectedFile();
		}
		
		return selectedFile;
	}

}

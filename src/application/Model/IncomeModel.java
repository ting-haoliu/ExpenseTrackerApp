package application.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.scene.control.TextArea;

public class IncomeModel {
	
	private static final String INCOMES_FILE = "incomes.txt";
	
	public static void createIncome(String username, String type, double amount, LocalDate date, String detail) {
		Income expense = new Income(username, type, amount, date, detail);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(INCOMES_FILE, true))){
			String expenseInfo = expense.toString();
			writer.write(expenseInfo);
			writer.newLine();
			
			System.out.println("Income added");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean removeIncome(String username, String type, double amount, LocalDate date, String detail) {
		Income income = new Income(username, type, amount, date, detail);
		
		File originalFile = new File(INCOMES_FILE);
		File tempFile = new File("temp.txt");
		String lineToRemove = income.toString();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
			String currentLine;
			boolean incomeRemoved = false;
			
			while((currentLine = reader.readLine()) != null){
				if(!currentLine.trim().equals(lineToRemove)) {
					writer.write(currentLine);
			        writer.newLine();
				} else {
					incomeRemoved = true;
				}
			}
			
			if (incomeRemoved) {
	            System.out.println("Income deleted");
	        } else {
	            System.out.println("Income not found");
	        }
			
			return incomeRemoved;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			originalFile.delete();
			tempFile.renameTo(originalFile);
		}
	}
	
	//Print out the income by month. the data will be placed in order by date
	public static void printIncomeByMonth(TextArea textArea, String username, int month) {
	    String targetUsername = username;
	    Map<Date, List<String>> incomesByDate = new TreeMap<>(Collections.reverseOrder());

	    try (BufferedReader reader = new BufferedReader(new FileReader(INCOMES_FILE))){
	        String line;

	        while((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");

	            String type = parts[1];
	            double amount = Double.parseDouble(parts[2]);
	            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);
	            String detail = parts[4];

	            String[] dateParts = new SimpleDateFormat("yyyy-MM-dd").format(date).split("-");
	            int incomeMonth = Integer.parseInt(dateParts[1]);

	            if (parts[0].equals(targetUsername) && incomeMonth == month) {
	                List<String> incomes = incomesByDate.getOrDefault(date, new ArrayList<>());
	                incomes.add(String.format("%-15s %-8s %-12s %-20s%n", type, amount, new SimpleDateFormat("yyyy-MM-dd").format(date), detail));
	                incomesByDate.put(date, incomes);
	            }
	        }

	        // Reverse order iteration
	        for (Map.Entry<Date, List<String>> entry : incomesByDate.entrySet()) {
	            for (String income : entry.getValue()) {
	                textArea.appendText(income);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static double getTotalIncomeByMonth(String username, int month) {
		String targetUsername = username;
		double totalIncome = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader("incomes.txt"))){
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				
				String[] dateParts = parts[3].split("-");
				int expenseMonth = Integer.parseInt(dateParts[1]);
				
				if (parts[0].equals(targetUsername) && expenseMonth == month) {
                    totalIncome += Double.parseDouble(parts[2]);
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalIncome;
	}
	
	public static double getIncomeByCategory(String username, int month, String category) {
		String targetUsername = username;
		String targetCategory = category;
		double totalIncome = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(INCOMES_FILE))){
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				
				String[] dateParts = parts[3].split("-");
	            int expenseMonth = Integer.parseInt(dateParts[1]);
				
				if(parts[0].equals(targetUsername) && expenseMonth == month && parts[1].equals(targetCategory)) {
					totalIncome += Double.parseDouble(parts[2]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalIncome;
	}
}

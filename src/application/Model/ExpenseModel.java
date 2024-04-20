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

public class ExpenseModel {

	private static final String EXPENSES_FILE = "expenses.txt";
	
	public static void createExpense(String username, String type, double amount, LocalDate date, String detail) {
		Expense expense = new Expense(username, type, amount, date, detail);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSES_FILE, true))){
			String expenseInfo = expense.toString();
			writer.write(expenseInfo);
			writer.newLine();
			
			System.out.println("Expense added");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean removeExpense(String username, String type, double amount, LocalDate date, String detail) {
		Expense expense = new Expense(username, type, amount, date, detail);
		
		File originalFile = new File(EXPENSES_FILE);
		File tempFile = new File("temp.txt");
		String lineToRemove = expense.toString();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
			String currentLine;
			boolean expenseRemoved = false;
			
			while((currentLine = reader.readLine()) != null){
				if(!currentLine.trim().equals(lineToRemove)) {
					writer.write(currentLine);
			        writer.newLine();
				} else {
					expenseRemoved = true;
				}
			}
			
			if (expenseRemoved) {
	            System.out.println("Expense deleted");
	        } else {
	            System.out.println("Expense not found");
	        }
			
			return expenseRemoved;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			originalFile.delete();
			tempFile.renameTo(originalFile);
		}
	}
	
	//print out the expense by month
	public static void printExpenseByMonth(TextArea textArea, String username, int month) {
		String targetUsername = username;
		Map<Date, List<String>> expensesByDate = new TreeMap<>(Collections.reverseOrder());
		
		try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSES_FILE))){
	        String line;

	        while((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");

	            String type = parts[1];
	            double amount = Double.parseDouble(parts[2]);
	            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]); // Corrected date format
	            String detail = parts[4];

	            String[] dateParts = new SimpleDateFormat("yyyy-MM-dd").format(date).split("-");
	            int expenseMonth = Integer.parseInt(dateParts[1]);

	            if (parts[0].equals(targetUsername) && expenseMonth == month) {
	                List<String> expenses = expensesByDate.getOrDefault(date, new ArrayList<>());
	                expenses.add(String.format("%-15s %-8s %-12s %-20s%n", type, amount, new SimpleDateFormat("yyyy-MM-dd").format(date), detail));
	                expensesByDate.put(date, expenses);
	            }
	        }

	        for (Map.Entry<Date, List<String>> entry : expensesByDate.entrySet()) {
	            for (String expense : entry.getValue()) {
	                textArea.appendText(expense);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static double getTotalExpenseByMonth(String username, int month) {
		String targetUsername = username;
		double totalExpense = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSES_FILE))){
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				
				String[] dateParts = parts[3].split("-");
				int expenseMonth = Integer.parseInt(dateParts[1]);
				
				if (parts[0].equals(targetUsername) && expenseMonth == month) {
                    totalExpense += Double.parseDouble(parts[2]);
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalExpense;
	}
	
	public static double getExpenseByCategory(String username, int month, String category) {
		String targetUsername = username;
		String targetCategory = category;
		double totalExpense = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSES_FILE))){
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				
				String[] dateParts = parts[3].split("-");
	            int expenseMonth = Integer.parseInt(dateParts[1]);
				
				if(parts[0].equals(targetUsername) && expenseMonth == month && parts[1].equals(targetCategory)) {
					totalExpense += Double.parseDouble(parts[2]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalExpense;
	}
}


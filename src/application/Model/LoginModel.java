package application.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginModel {
	
	public static boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[2];
                String storedPassword = parts[3];
                
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}

package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String password;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE") // Ensure default value
    private boolean isVerified = false; 

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.isVerified = false; // Explicitly set default value
    }

	public void setVerified(boolean isVerified) {
		 this.isVerified = isVerified;
		
	}
}




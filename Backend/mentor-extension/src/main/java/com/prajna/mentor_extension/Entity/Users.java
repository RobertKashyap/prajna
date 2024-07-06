package com.prajna.mentor_extension.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import com.prajna.mentor_extension.Exchanges.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Users {
    @Id
    @NonNull
    String id;
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String password;

    private Boolean active;

    Dashboard dashboard;
    String presentQueryHash;
}

//http://localhost:8080/api/endpoint

/*


frontend--->current text, name, email, id--> if(id) -->cleanedData{curHash!=prevHash} --> ai --> dashboard.json --> user.save(dashboard) --> userPOJO --> dashboard --> frontend 
                                                                |
                                                                v
                                                         --------------------------       
                                                        |     From old data in db  |
                                                        ---------------------------

request body {
                String:currentText
             }

response body {
                Dashboard
              }
 
              */
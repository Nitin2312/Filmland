package com.entertainment.filmland.model;

import com.entertainment.filmland.security.GenerateHash;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonDeserialize
@JsonSerialize
public class UserDetails {

	
    private String email;
    @GenerateHash
    private  String password;
}

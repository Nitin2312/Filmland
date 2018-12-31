
package com.entertainment.filmland.model;

import lombok.Builder;
import lombok.Data;




@Data
@Builder
public class LoginCredentials {

    private boolean isValid;
    private String authKey;
}

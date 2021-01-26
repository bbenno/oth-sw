package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    /**
     * The name.
     */
    private final String authority;
    
}

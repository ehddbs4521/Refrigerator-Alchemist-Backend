package studybackend.refrigeratorcleaner.Entity;


import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BoardId implements Serializable {
    private Long id;
    private String nickName;

    // Constructors, equals, hashCode methods, and getters/setters
    // Make sure to implement Serializable interface
}

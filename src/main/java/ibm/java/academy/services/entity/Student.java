package ibm.java.academy.services.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Eduardo Cortés
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", length = 16, unique = true, nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME ", length = 255, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME ", length = 255, nullable = false)
    private String lastName;

    @Column(name = "BIRTH_DATE", columnDefinition = "date")
    private LocalDate birthDate;

    @Column(name = "GENDER ", length = 1, nullable = false)
    private char gender;

}

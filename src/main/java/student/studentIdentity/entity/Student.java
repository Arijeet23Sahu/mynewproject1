package student.studentIdentity.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.intellij.lang.annotations.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String lastName;

    @NotNull

    private LocalDate dob;

    @NotNull
    private String section;

    @NotNull
    private String gender;

    private Integer marks1;
    private Integer marks2;
    private Integer marks3;

    private Integer total;
    private Double average;
    private String result;

    // Constructors, getters, setters, and other methods
}

package student.studentIdentity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.studentIdentity.entity.Student;
import student.studentIdentity.repository.StudentRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody @Valid Student student) {
        // Validate age
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(student.getDob(), currentDate).getYears();
        if (age <= 15 || age > 20) {
            return ResponseEntity.badRequest().body("Age should be between 15 and 20 years.");
        }

        // Validate marks
        if (student.getMarks1() != null && (student.getMarks1() < 0 || student.getMarks1() > 100) ||
                student.getMarks2() != null && (student.getMarks2() < 0 || student.getMarks2() > 100) ||
                student.getMarks3() != null && (student.getMarks3() < 0 || student.getMarks3() > 100)) {
            return ResponseEntity.badRequest().body("Marks should be between 0 and 100.");
        }

        // Calculate total, average, and result
        int totalMarks = (student.getMarks1() != null ? student.getMarks1() : 0) +
                (student.getMarks2() != null ? student.getMarks2() : 0) +
                (student.getMarks3() != null ? student.getMarks3() : 0);

        double averageMarks = totalMarks / 3.0;
        String result = (student.getMarks1() >= 35 && student.getMarks2() >= 35 && student.getMarks3() >= 35)
                ? "Pass" : "Fail";

        student.setTotal(totalMarks);
        student.setAverage(averageMarks);
        student.setResult(result);

        studentRepository.save(student);

        return ResponseEntity.ok("Student created successfully.");
    }

    @PutMapping("/{id}/update-marks")
    public ResponseEntity<?> updateStudentMarks(@PathVariable Long id, @RequestBody @Valid Student updatedStudent) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();

        // Update marks
        student.setMarks1(updatedStudent.getMarks1());
        student.setMarks2(updatedStudent.getMarks2());
        student.setMarks3(updatedStudent.getMarks3());

        // Recalculate total, average, and result
        int totalMarks = (student.getMarks1() != null ? student.getMarks1() : 0) +
                (student.getMarks2() != null ? student.getMarks2() : 0) +
                (student.getMarks3() != null ? student.getMarks3() : 0);

        double averageMarks = totalMarks / 3.0;
        String result = (student.getMarks1() >= 35 && student.getMarks2() >= 35 && student.getMarks3() >= 35)
                ? "Pass" : "Fail";

        student.setTotal(totalMarks);
        student.setAverage(averageMarks);
        student.setResult(result);

        studentRepository.save(student);

        return ResponseEntity.ok("Student marks updated successfully.");
    }
}

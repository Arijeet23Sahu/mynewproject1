package student.studentIdentity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.studentIdentity.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

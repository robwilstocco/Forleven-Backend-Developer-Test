package br.com.forleven.studentapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.forleven.studentapi.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}

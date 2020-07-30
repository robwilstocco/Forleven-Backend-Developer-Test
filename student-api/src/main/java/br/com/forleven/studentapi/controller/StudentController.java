package br.com.forleven.studentapi.controller;

import br.com.forleven.studentapi.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.forleven.studentapi.repository.StudentRepository;


@RestController
public class StudentController {

	@Autowired
	private StudentRepository _studentRepository;
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<Student> Get(){
		return _studentRepository.findAll();
	}
	
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> GetById(@PathVariable(value = "id") Integer id)
    {
        Optional<Student> student = _studentRepository.findById(id);
        if(student.isPresent())
            return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ResponseEntity<Student> Post(@Valid @RequestBody Student student){
		Integer id = student.getMatricula();
		Optional<Student> matricula = _studentRepository.findById(id);
		if(matricula.isPresent()) {
			return new ResponseEntity<Student>(HttpStatus.CONFLICT);
		}
		if(student.getNome().length() < 3 || student.getSobrenome().length() < 3 || student.getMatricula().toString().length() < 3 ) {
    		return new ResponseEntity<Student>(HttpStatus.LENGTH_REQUIRED);
    	}else {
    		_studentRepository.save(student);
    		return new ResponseEntity<Student>(student, HttpStatus.OK);
    	}
	}
	
    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Student> Put(@PathVariable(value = "id") Integer id, @Valid @RequestBody Student newStudent){
        Optional<Student> oldStudent = _studentRepository.findById(id);
        if(oldStudent.isPresent()){
            Student student = oldStudent.get();
            student.setNome(newStudent.getNome());
            student.setSobrenome(newStudent.getSobrenome());
            _studentRepository.save(student);
            return new ResponseEntity<Student>(student, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") Integer id)    {
        Optional<Student> student = _studentRepository.findById(id);
        if(student.isPresent()){
            _studentRepository.delete(student.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

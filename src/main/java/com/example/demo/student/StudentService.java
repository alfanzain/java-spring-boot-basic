package com.example.demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
	
	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository
			.findStudentByEmail(student.getEmail());
		
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("Email taken");
		}
		
		studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		boolean exists = studentRepository.existsById(studentId);
		
		if (!exists) {
			throw new IllegalStateException("Student with id " + studentId + " does not exists");
		}

		studentRepository.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId, Student newStudent) {
		Student student = studentRepository.findById(studentId)
			.orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));

		if (newStudent.getName() != null && newStudent.getName().length() > 0 && !Objects.equals(student.getName(), newStudent.getName())) {
			student.setName(newStudent.getName());
		}
		
		if (newStudent.getEmail() != null && newStudent.getEmail().length() > 0 && !Objects.equals(student.getEmail(), newStudent.getEmail())) {
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(newStudent.getEmail());

			if (studentOptional.isPresent()) {
				throw new IllegalStateException("Email taken");
			}

			student.setEmail(newStudent.getEmail());
		}
	}
}

package fr.insa.mas.studentManagementMS.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.mas.studentManagementMS.model.Student;

@RestController
@RequestMapping("/students")
public class StudentRessource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/students")
	public int studentNumber() {
		return 200;
	}
	
	@GetMapping(value="/students/{id}")
	public Student infosStudent(@PathVariable int id) {
		Student student = new Student(id, "Dupont", "Jean");
		return student;
	}
}
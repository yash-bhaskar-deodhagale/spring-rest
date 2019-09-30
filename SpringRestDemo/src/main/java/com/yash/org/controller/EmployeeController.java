package com.yash.org.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yash.org.exception.EmployeeNotFoundException;
import com.yash.org.model.Employee;
import com.yash.org.service.EmployeeService;

@RestController()
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(path = "/employees", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployees();
	}

	@GetMapping(path = "/employees/{empId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public Employee getEmployeeById(@PathVariable int empId) {
		Employee employee = employeeService.getEmployeeById(empId);

		if (employee == null) {
			throw new EmployeeNotFoundException("Employee id not found -" + empId);
		}
		return employee;
	}

	@PostMapping(path = "/employees", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee addEmployee(@RequestBody Employee employee) {
		employeeService.addEmployee(employee);
		return employee;
	}

	@PutMapping(path = "/employees", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee updateEmployee(@RequestBody Employee employee) {
		Employee emp = employeeService.getEmployeeById(employee.getEmpId());
		if (emp == null) {
			throw new EmployeeNotFoundException("Employee not found to Update");
		}
		employeeService.updateEmployee(employee);
		return employee;
	}

	@DeleteMapping(path = "/employees/{empId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Employee> deleteEmployee(@PathVariable int empId) {
		Employee employee = employeeService.getEmployeeById(empId);
		if (employee == null) {
			throw new EmployeeNotFoundException("Employee id not found -" + empId);
		}
		employeeService.deleteEmployee(empId);
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);

	}

}

package com.yash.org.service;

import java.util.List;

import com.yash.org.model.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees();

	public Employee getEmployeeById(int empId);

	public void addEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public void deleteEmployee(int empId);
}

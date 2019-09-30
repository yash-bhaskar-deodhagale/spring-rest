package com.yash.org.dao;

import java.util.List;

import com.yash.org.model.Employee;

public interface EmployeeDao {
	public List<Employee> getAllEmployees();

	public Employee getEmployeeById(int empId);

	public void addEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public void deleteEmployee(int empId);
}

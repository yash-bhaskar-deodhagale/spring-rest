package com.yash.org.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yash.org.dao.EmployeeDao;
import com.yash.org.model.Employee;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	public Employee getEmployeeById(int empId) {
		return employeeDao.getEmployeeById(empId);
	}

	public void addEmployee(Employee employee) {
		employeeDao.addEmployee(employee);
	}

	public void updateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee);
	}

	public void deleteEmployee(int empId) {
		employeeDao.deleteEmployee(empId);
	}

}

package com.yash.org.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.org.model.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Employee> getAllEmployees() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Employee> query=session.createQuery("from Employee");
		return query.getResultList();
	}

	@Override
	public Employee getEmployeeById(int empId) {
		Session session=sessionFactory.getCurrentSession();
		Employee employee=session.get(Employee.class,empId);
		return employee;
	}

	@Override
	public void addEmployee(Employee employee) {
		Session session=sessionFactory.getCurrentSession();
		session.save(employee);
	}

	@Override
	public void updateEmployee(Employee employee) {
		Session session=sessionFactory.getCurrentSession();
		session.update(employee);
	}

	@Override
	public void deleteEmployee(int empId) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		Query deletequery=session.createQuery("delete from Employee where empId=:empId");
		deletequery.setParameter("empId", empId);
		deletequery.executeUpdate();
		
	}

}

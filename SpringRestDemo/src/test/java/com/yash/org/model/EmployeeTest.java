package com.yash.org.model;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.yash.org.model.Employee;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeTest {
	
	@InjectMocks
    private Employee employee = new Employee();

    @Test
    public void testEmployeeToString() {
                    Employee emp = new Employee(1, "Virat", "kohli", 1111);
                    String str = emp.toString();
                    assertEquals(emp.toString(), str);
    }

    @Test
    public void testEmployeeEqualsAndHashcode() {
                    Employee e1 = new Employee(1, "Virat", "kohli", 1111);
                    Employee e2 = new Employee(2, "Rohit", "kohli", 2344);

                    System.out.println("---------" + e1.hashCode());
                    System.out.println("---------" + e2.hashCode());
                    EqualsVerifier.forExamples(e1, e2).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();


}
}

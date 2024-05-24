package br.edu.uniara.lip2.rest.Model;

import org.springframework.data.jpa.repository.JpaRepository;

    public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

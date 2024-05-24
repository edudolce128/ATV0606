package br.edu.uniara.lip2.rest.controller;

import br.edu.uniara.lip2.rest.Model.Employee;

import br.edu.uniara.lip2.rest.Model.EmployeePagingRepository;
import br.edu.uniara.lip2.rest.Model.EmployeeRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeePagingRepository pagingRepository;



    @GetMapping("/{id}")
    public Employee one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Erro pesquisando id: " + id));
    }
    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam int page,
            @RequestParam int size
    ) {
        if (page < 0) {
            return ResponseEntity.badRequest().body("page deve ser > 0");
        }

        if (size < 0 || size > 500 ){
            return ResponseEntity.badRequest().body("page deve ser > 1 || <= 500");
        }

        Pageable pageable = PageRequest.of(page, size);

        final Page<Employee> listEmployee =
                pagingRepository.findAll(pageable);
        return ResponseEntity.ok(listEmployee.stream().toArray());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable Long id) {
         var employeeExists = repository.existsById(id);
         if (!employeeExists )
             return ResponseEntity.notFound().build();


         repository.deleteById(id);

         return ResponseEntity.ok(  id + "was removed");
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@RequestBody Employee employee){
        var EmployeeSalvo = repository.save(employee);

        return ResponseEntity.ok(EmployeeSalvo);
    }

}

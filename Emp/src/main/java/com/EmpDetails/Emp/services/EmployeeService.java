package com.EmpDetails.Emp.services;

import com.EmpDetails.Emp.dto.EmployeeDto;
import com.EmpDetails.Emp.entities.EmployeeEntity;
import com.EmpDetails.Emp.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    final ModelMapper modelMapper;

    EmployeeRepository employeeRepository;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository)
    {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDto getEmployeeById(Long id)
    {
       EmployeeEntity employeeEntity= employeeRepository.getById(id);

       return modelMapper.map(employeeEntity,EmployeeDto.class);
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto,EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDto.class);

    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().
                stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).collect(Collectors.toList());


    }

    public boolean deleteEmployeeById(Long id) {
       boolean isPresent= employeeRepository.existsById(id);
       if(isPresent){
           employeeRepository.deleteById(id);
           return true;
       }
       else
           return false;

    }
}

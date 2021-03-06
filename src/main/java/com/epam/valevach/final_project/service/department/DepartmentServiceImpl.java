package com.epam.valevach.final_project.service.department;

import com.epam.valevach.final_project.dao.department.DepartmentDAO;
import com.epam.valevach.final_project.dao.department.DepartmentDAOImpl;
import com.epam.valevach.final_project.entity.Department;
import com.epam.valevach.final_project.exceptions.CreateDepartmentException;
import com.epam.valevach.final_project.exceptions.DeleteDepartmentException;
import com.epam.valevach.final_project.service.employee.EmployeeServiceImpl;
import com.epam.valevach.final_project.validator.UserInputValidation;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private static DepartmentServiceImpl instance;
    DepartmentDAO departmentDAO = DepartmentDAOImpl.getInstance();

    private DepartmentServiceImpl() {
    }

    public static DepartmentServiceImpl getInstance() {
        synchronized (DepartmentServiceImpl.class) {
            if (instance == null) {
                instance = new DepartmentServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void create(Department entity) {
        UserInputValidation validation = UserInputValidation.getInstance();
        if (!validation.instance.checkLengthLimit(entity.getDepName()) ||
                !validation.instance.checkLengthLimit(entity.getDepPosition())) {
            throw new CreateDepartmentException();
        }
        departmentDAO.create(entity);
    }

    @Override
    public Department read(Integer id) {
        return departmentDAO.read(id);
    }

    @Override
    public void delete(Department entity) {
        EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();
        if(employeeService.readEmployeesByDepartment(entity.getDepId()).size()>0){
            throw new DeleteDepartmentException();
        }
        departmentDAO.delete(entity);
    }

    @Override
    public void update(Department entity) {
        departmentDAO.update(entity);
    }

    @Override
    public List<Department> showAllDepartments() {
        return departmentDAO.showAllDepartments();
    }
}

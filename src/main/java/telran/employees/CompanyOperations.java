package telran.employees;
import telran.employees.storages.PlainFile;
import telran.io.Persistable;
import telran.net.Protocol;
import telran.net.Response;
import telran.net.TCPServer;

import java.util.Arrays;

import static telran.net.ResponseCode.*;

public class CompanyOperations implements Runnable
{
    private final Server server;

    private final Protocol PROTOCOL = request -> {
        Response response;
        if (request.requestType() != null) {
            response = switch (request.requestType().toLowerCase()) {
                case "hire_employee" -> hireEmployee(request.requestData());
                case "hire_wage_employee" -> hireWageEmployee(request.requestData());
                case "hire_sales_person" -> hireSalesPerson(request.requestData());
                case "hire_manager" -> hireManager(request.requestData());
                case "fire_employee" -> fireEmployee(request.requestData());
                case "get_employees" -> getEmployees(request.requestData());
                case "get_employee_by_id" -> getEmployeeById(request.requestData());
                case "get_employees_by_department" -> getEmployeesByDepartment(request.requestData());
                case "get_employees_by_age" -> getEmployeesByAge(request.requestData());
                case "get_department_salary_budget" -> getDepartmentSalaryBudget(request.requestData());
                case "get_departments_list" -> getDepartmentsList(request.requestData());
                case "get_managers_most_factors" -> getManagersMostFactors(request.requestData());
                default -> new Response(WRONG_REQUEST, String.format("Wrong request type: %s", request.requestType()));
            };
        } else {
            response = new Response(WRONG_DATA, "Wrong request data, null given");
        }
        return response;
    };

    private static Response getManagersMostFactors(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getManagersMostFactors() not implemented yet");
    }

    private static Response getDepartmentsList(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getDepartmentsList() not implemented yet");
    }

    private static Response getDepartmentSalaryBudget(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getDepartmentSalaryBudget() not implemented yet");
    }

    private static Response getEmployeesByAge(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployeesByAge() not implemented yet");
    }

    private static Response getEmployeesByDepartment(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployeesByDepartment() not implemented yet");
    }

    private static Response getEmployeeById(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployeeById() not implemented yet");
    }

    private static Response getEmployees(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployees() not implemented yet");
    }

    private static Response fireEmployee(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.fireEmployee() not implemented yet");
    }

    private Response hireManager(String json_data)
    {
        Response res;

        try {
            Company company = server.getCompany();
            Manager employee = (Manager) Manager.getEmployeeFromJSON(json_data);
            company.addEmployee(employee);
            res = new Response(SUCCESS, "Manager added");
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response hireSalesPerson(String json_data)
    {
        Response res;

        try {
            Company company = server.getCompany();
            SalesPerson employee = (SalesPerson) SalesPerson.getEmployeeFromJSON(json_data);
            company.addEmployee(employee);
            res = new Response(SUCCESS, "Sales Person added");
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response hireWageEmployee(String json_data)
    {
        Response res;

        try {
            Company company = server.getCompany();
            WageEmployee employee = (WageEmployee) WageEmployee.getEmployeeFromJSON(json_data);
            company.addEmployee(employee);
            res = new Response(SUCCESS, "Wage Employee added");
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response hireEmployee(String json_data)
    {
        Response res;

        try {
            Company company = server.getCompany();
            Employee employee = Employee.getEmployeeFromJSON(json_data);
            company.addEmployee(employee);
            res = new Response(SUCCESS, "Employee added");
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    public CompanyOperations(Server server) {
        this.server = server;
    }

    @Override
    public void run()
    {
        boolean running = true;
        if (server.getCompany() instanceof Persistable persistable_company) {
            try {
                if (server.getCompany() == null) {
                    server.setCompany(restoreCompany());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            while(running) {
                try {
                    TCPServer tcp_server = new TCPServer(PROTOCOL, server.PORT);
                    tcp_server.run();
                } catch (Exception e) {
                    System.out.println("Client closed connection abnormally");
                    System.err.println(e.getMessage() + " \n " + Arrays.toString(e.getStackTrace()));
                    running = false;
                }
            }

        }
    }

    private Company restoreCompany()
    {
        return new PlainFile().load();
    }
}

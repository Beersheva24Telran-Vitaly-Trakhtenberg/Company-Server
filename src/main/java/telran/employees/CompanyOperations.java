package telran.employees;
import telran.employees.storages.PlainFileStorage;
import telran.io.Persistable;
import telran.net.*;

import static telran.net.ResponseCode.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CompanyOperations implements Runnable, Protocol
{
    private final Server server;
    private final int port;

    private Response getManagersMostFactors(String s)
    {
        Response res;

        try {
            Company company = server.getCompany();
            Manager[] managers = company.getManagersWithMostFactor();
            res = new Response(SUCCESS, Arrays.toString(managers));
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response getDepartmentsList(String s)
    {
        Response res;

        try {
            Company company = server.getCompany();
            String[] departments = company.getDepartments();
            res = new Response(SUCCESS, Arrays.toString(departments));
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response getDepartmentSalaryBudget(String s)
    {
        Response res;

        try {
            Company company = server.getCompany();
            int department_budget = company.getDepartmentBudget(s);
            res = new Response(SUCCESS, department_budget+"");
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private static Response getEmployeesByAge(String s) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployeesByAge() not implemented yet");
    }

    private Response getEmployeesByDepartment(String s)
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployeesByDepartment() not implemented yet");
    }

    private Response getEmployeeById(String s)
    {
        Response res;

        try {
            Company company = server.getCompany();
            Long emlpoyee_id = Long.parseLong(s);
            CompanySettings.validateEmployeeID(emlpoyee_id);
            Employee employee = company.getEmployee(emlpoyee_id);
            res = new Response(SUCCESS, employee.toString());
        } catch (NullPointerException e) {
            res = new Response(NOT_FOUND, "No data for employee id: " + s);
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response getEmployees(String s)
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyOperations.getEmployees() not implemented yet");
    }

    private Response fireEmployee(String s)
    {
        Response res;

        try {
            Company company = server.getCompany();
            Long emlpoyee_id = Long.parseLong(s);
            CompanySettings.validateEmployeeID(emlpoyee_id);
            company.removeEmployee(emlpoyee_id);
            res = new Response(SUCCESS, "Employee fired");
            server.setDataChanged(true);
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    private Response hireManager(String json_data)
    {
        Response res;

        try {
            Company company = server.getCompany();
            Manager employee = (Manager) Manager.getEmployeeFromJSON(json_data);
            company.addEmployee(employee);
            res = new Response(SUCCESS, "Manager added");
            server.setDataChanged(true);
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
            server.setDataChanged(true);
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
            server.setDataChanged(true);
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
            server.setDataChanged(true);
        } catch (Exception e) {
            res = new Response(INTERNAL_ERROR, e.getMessage());
        }

        return res;
    }

    public CompanyOperations(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run()
    {
        if (server.getCompany() instanceof Persistable persistable_company) {
            try {
                if (server.getCompany() == null) {
                    server.setCompany(restoreCompany());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                TCPServer tcp_server = new TCPServer(this, this.port);
                tcp_server.run();
            } catch (Exception e) {
                System.out.println("Client closed connection abnormally");
                System.err.println(e.getMessage() + " \n " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    @Override
    public Response getResponse(Request request)
    {
        String requestType = request.requestType();
        String requestData = request.requestData();
        Response response = null;
        try {
            Method method = CompanyOperations.class.getDeclaredMethod(requestType, String.class);
            method.setAccessible(true);
            response = (Response) method.invoke(this, requestData);
        } catch (NoSuchMethodException e) {
            response = new Response(ResponseCode.WRONG_REQUEST, requestType + " Wrong type");

        } catch (InvocationTargetException e) {
            Throwable causeExc = e.getCause();
            String message = causeExc == null ? e.getMessage() : causeExc.getMessage();
            response = new Response(ResponseCode.WRONG_DATA, message);
        } catch (Exception e){
            //Note: using only for shut down the Server and printing out Exception full stack
            throw new RuntimeException(e);
        }

        return response;
    }

    Response getOkResponse(String responseData) {
        return new Response(ResponseCode.SUCCESS, responseData);
    }

    private Company restoreCompany()
    {
        return new PlainFileStorage(server).load();
    }
}

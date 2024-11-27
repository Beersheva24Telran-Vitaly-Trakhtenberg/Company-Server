package telran.employees;

public class CompanySettings
{
    public final static long MIN_EMPLOYEE_ID = 100000;
    public final static long MAX_EMPLOYEE_ID = 999999;
    public final static int MIN_BASIC_SALARY = 5000;
    public final static int MAX_BASIC_SALARY = 30000;
    public final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };
    public final static int MIN_EMPLOYEE_AGE = 18;
    public final static int MAX_EMPLOYEE_AGE = 70;
    public final static int MIN_WAGE = 5;
    public final static int MAX_WAGE = 50;
    public final static int MIN_HOURS = 1;
    public final static int MAX_HOURS = 40;
    public final static float MIN_PERCENT = 10;
    public final static float MAX_PERCENT = 100;
    public final static long MIN_SALES = 5;
    public final static long MAX_SALES = 99999;
    public final static double MIN_FACTOR = 0.1;
    public final static double MAX_FACTOR = 1.0;

    public static void validateEmployeeID(long id) {
        if (id < MIN_EMPLOYEE_ID || id > MAX_EMPLOYEE_ID) {
            throw new IllegalArgumentException("Employee ID should be between " + MIN_EMPLOYEE_ID + " and " + MAX_EMPLOYEE_ID);
        }
    }
}

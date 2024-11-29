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

    public static void validateBasicSalary(int salary) {
        if (salary < MIN_BASIC_SALARY || salary > MAX_BASIC_SALARY) {
            throw new IllegalArgumentException("Basic salary should be between " + MIN_BASIC_SALARY + " and " + MAX_BASIC_SALARY);
        }
    }

    public static void validateEmployeeAge(int age) {
        if (age < MIN_EMPLOYEE_AGE || age > MAX_EMPLOYEE_AGE) {
            throw new IllegalArgumentException("Employee age should be between " + MIN_EMPLOYEE_AGE + " and " + MAX_EMPLOYEE_AGE);
        }
    }

    public static void validateWage(int wage) {
        if (wage < MIN_WAGE || wage > MAX_WAGE) {
            throw new IllegalArgumentException("Wage should be between " + MIN_WAGE + " and " + MAX_WAGE);
        }
    }

    public static void validateHours(int hours) {
        if (hours < MIN_HOURS || hours > MAX_HOURS) {
            throw new IllegalArgumentException("Hours should be between " + MIN_HOURS + " and " + MAX_HOURS);
        }
    }

    public static void validatePercent(float percent) {
        if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
            throw new IllegalArgumentException("Percent should be between " + MIN_PERCENT + " and " + MAX_PERCENT);
        }
    }

    public static void validateSales(long sales) {
        if (sales < MIN_SALES || sales > MAX_SALES) {
            throw new IllegalArgumentException("Sales should be between " + MIN_SALES + " and " + MAX_SALES);
        }
    }

    public static void validateFactor(double factor) {
        if (factor < MIN_FACTOR || factor > MAX_FACTOR) {
            throw new IllegalArgumentException("Factor should be between " + MIN_FACTOR + " and " + MAX_FACTOR);
        }
    }

}

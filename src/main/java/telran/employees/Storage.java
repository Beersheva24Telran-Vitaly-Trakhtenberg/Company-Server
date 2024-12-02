package telran.employees;

public interface Storage
{
    void save(Company company);
    Company load();
}

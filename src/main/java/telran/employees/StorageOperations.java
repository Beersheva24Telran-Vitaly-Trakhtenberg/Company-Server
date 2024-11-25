package telran.employees;

public interface StorageOperations
{
    void save(Company company);
    Company load();
}

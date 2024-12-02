package telran.employees.storages;

import telran.employees.Company;

public interface Storage
{
    void save(Company company);
    Company load() throws Exception;
}

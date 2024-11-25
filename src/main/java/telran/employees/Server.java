package telran.employees;

import telran.employees.storages.PlainFile;
import telran.io.Persistable;

public class Server
{
    private volatile Company company = new CompanyImpl();
    public final int PORT = 3500;
    private volatile boolean data_changed = false;


    public static void main(String[] args) {

    }

    private Server() {

        if (this.company instanceof Persistable persistable_company) {
            try {
                CompanyOperations company_operations = new CompanyOperations(this);

                Thread thread_operations = new Thread(company_operations);
                //Thread thread_storage = new Thread(new PlainFile(this));

                // FixMe: add shutdown hook to save company to file
/*
                PersistableSaverThread saverThread = new PersistableSaverThread(persistable_company, FILE_NAME, TIME_INTERVAL);
                saverThread.start();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> persistable_company.saveToFile(FILE_NAME)));
*/

                thread_operations.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getDataChanged() {
        return this.data_changed;
    }

    public void setDataChanged(boolean data_changed) {
        this.data_changed = data_changed;
    }

    public Company getCompany()
    {
        return this.company;
    }

    public void setCompany(Company company)
    {
        this.company = company;
    }
}

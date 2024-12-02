package telran.employees;

import org.json.JSONObject;
import telran.employees.storages.PlainFileStorage;
import telran.io.Persistable;

public class Server
{
    private volatile Company company = new CompanyImpl();

    public final int PORT_FROM = 3500;
    public final int PORT_TO = 3510;
    private volatile boolean data_changed = false;


    public static void main(String[] args)
    {
        new Server("file");
    }

    private Storage storage = null;

    private Server(String storage_type) {

        if (this.company instanceof Persistable) {
            try {
                JSONObject storage_settings = new JSONObject();
                storage_settings.put("FILE_NAME", "employees.data");
                storage_settings.put("DIRECTORY_NAME", "CompanyData");
                storage = new StorageFactory().createStorage(storage_settings, this, storage_type);

                company = storage.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int port = PORT_FROM; port <= PORT_TO; port++) {
                    CompanyOperations company_operations = new CompanyOperations(this, port);
                    Thread thread_operations = new Thread(company_operations);
                    thread_operations.start();
                }

                Thread thread_storage = new Thread((Runnable) storage);
                thread_storage.start();
                Storage finalStorage = storage;
                Company finalCompany = this.company;
                Runtime.getRuntime().addShutdownHook(new Thread(() -> finalStorage.save(finalCompany)));
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

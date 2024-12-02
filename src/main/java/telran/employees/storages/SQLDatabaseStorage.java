package telran.employees.storages;

import org.json.JSONObject;
import telran.employees.*;

import java.io.IOException;


public class SQLDatabaseStorage  extends CompanyStorage implements Runnable
{
    private final String connectionString;

    public SQLDatabaseStorage(JSONObject storage_settings, Server server)
    {
        super(storage_settings, server);
        this.connectionString = storage_settings.getString("CONNECTION_STRING");
    }

    @Override
    public void save(Company company) {
        // TODO save to the database
    }

    @Override
    public Company load() {
        // TODO restore from the database
        return new CompanyImpl();   // FixMe
    }

    @Override
    public void run() {
        System.out.println("Storage Server Started. Chosen type: Database");
        while (true) {
            try {
                Thread.sleep(TIME_INTERVAL);
                if (server.getDataChanged()) {
                    save(server.getCompany());
                    System.out.println("Company saved to database");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
package telran.employees;

import org.json.JSONObject;
import telran.employees.storages.Storage;

public class CompanyStorage implements Storage, Runnable
{
    protected final Server server;
    protected final int TIME_INTERVAL = 1 * 60 * 1000;
    protected final JSONObject storage_settings;

    public CompanyStorage(JSONObject storage_settings, Server server)
    {
        this.server = server;
        this.storage_settings = storage_settings;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run()
    {

    }

    @Override
    public void save(Company company) {

    }

    @Override
    public Company load() {
        return null;
    }
}

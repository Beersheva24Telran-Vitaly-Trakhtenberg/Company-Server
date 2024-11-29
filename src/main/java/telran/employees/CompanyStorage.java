package telran.employees;

public class CompanyStorage implements Storage, Runnable
{
    protected final Server server;
    protected final int TIME_INTERVAL = 1 * 60 * 1000;

    public CompanyStorage(Server server) {
        this.server = server;
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

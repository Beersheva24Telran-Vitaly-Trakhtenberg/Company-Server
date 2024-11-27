package telran.employees.storages;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.employees.Server;
import telran.employees.Storage;

import java.io.File;
import java.nio.file.Paths;

public class PlainFileStorage implements Storage, Runnable
{
    private final String FILE_NAME;
    private final String DIRECTORY_NAME;
    private final int TIME_INTERVAL = 1 * 60 * 1000;
    private final Server server;

    public PlainFileStorage(String file_name, String directory_name, Server server)
    {
        this.FILE_NAME = file_name;
        this.DIRECTORY_NAME = directory_name;
        this.server = server;
    }

    public PlainFileStorage(Server server)
    {
        this("employees.data", "CompanyData", server);
    }

    public String getFilePath()
    {
        String res = null;
        String projectRoot = Paths.get("").toAbsolutePath().toString();
        String file_path = Paths.get(projectRoot, DIRECTORY_NAME, FILE_NAME).toString();

        File file = new File(file_path);
        if (file.exists()) {
            res = file.getAbsolutePath();
        }

        return res;
    }

    @Override
    public void save(Company company)
    {
        ((CompanyImpl) company).saveToFile(getFilePath());
    }

    @Override
    public Company load()
    {
        Company company = new CompanyImpl();
        String file_path = getFilePath();
        if (file_path != null) {
            ((CompanyImpl) company).restoreFromFile(file_path);
        }

        return company;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run()
    {
        while (true) {
            try {
                Thread.sleep(TIME_INTERVAL);
                if (server.getDataChanged()) {
                    save(server.getCompany());
                    System.out.println("Company saved to file");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

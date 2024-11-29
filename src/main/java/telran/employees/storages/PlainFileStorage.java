package telran.employees.storages;

import telran.employees.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PlainFileStorage extends CompanyStorage implements Runnable
{
    private final String FILE_NAME;
    private final String DIRECTORY_NAME;

    public PlainFileStorage(String file_name, String directory_name, Server server)
    {
        super(server);
        this.FILE_NAME = file_name;
        this.DIRECTORY_NAME = directory_name;
    }

    public PlainFileStorage(Server server)
    {
        this("employees.data", "CompanyData", server);
    }

    public String getFilePath() throws IOException {
        String res = null;
        String project_root = Paths.get("").toAbsolutePath().toString();
        String file_path = Paths.get(project_root, DIRECTORY_NAME, FILE_NAME).toString();

        File directory = new File(Paths.get(project_root, DIRECTORY_NAME).toString());
        boolean mkdir_exists = directory.exists();
        if (!mkdir_exists) {
            mkdir_exists = directory.mkdirs();
        }
        if (mkdir_exists) {
            File file = new File(file_path);
            if (!file.exists()) {
                file.createNewFile();
            }
            res = file.getAbsolutePath();
        }

        return res;
    }

    @Override
    public void save(Company company) {
        try {
            String path = getFilePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ((CompanyImpl) company).saveToFile(getFilePath());
            System.out.println("Company saved to the file " + getFilePath());
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company load()   // FIXME: fix this method
    {
        Company company = new CompanyImpl();
        String file_path = null;
        try {
            file_path = getFilePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        System.out.println("Storage Server Started. Chosen type: PlainFile");
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

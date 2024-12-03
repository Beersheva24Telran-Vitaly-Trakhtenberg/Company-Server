package telran.employees.storages;

import org.json.JSONObject;
import telran.employees.*;
import telran.io.Persistable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;

public class PlainFileStorage extends CompanyStorage implements Runnable
{
    private final String FILE_NAME;
    private final String DIRECTORY_NAME;

    public PlainFileStorage(JSONObject storage_settings, Server server)
    {
        super(storage_settings, server);
        this.FILE_NAME = storage_settings.getString("FILE_NAME");
        this.DIRECTORY_NAME = storage_settings.getString("DIRECTORY_NAME");
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
    public synchronized void save(Company company) {
        try {
            if (company instanceof Persistable) {
                String datafile_path;
                try {
                    datafile_path = getFilePath();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((CompanyImpl) company).saveToFile(datafile_path);
                System.out.println("Company saved to the file " + datafile_path);
            } else {
                throw new IllegalArgumentException("Company isn't Persistable");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Company load()
    {
        Company company = new CompanyImpl();
        try {
            ((CompanyImpl) company).restoreFromFile(getFilePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                    server.resetDataChanged();
                    System.out.println("Company saved to file");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package telran.employees.storages;

import java.io.File;
import java.nio.file.Paths;

public class PlainFile implements Runnable
{
    private final String FILE_NAME;
    private final String DIRECTORY_NAME;

    public PlainFile(String file_name, String directory_name)
    {
        this.FILE_NAME = file_name;
        this.DIRECTORY_NAME = directory_name;
    }

    public PlainFile()
    {
        this("employees.data", "CompanyData");
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
    public void run() {

    }
}

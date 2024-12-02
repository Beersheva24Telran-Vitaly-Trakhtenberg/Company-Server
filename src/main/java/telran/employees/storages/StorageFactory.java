package telran.employees.storages;

import org.json.JSONObject;
import telran.employees.Server;

public class StorageFactory
{
    public static Storage createStorage(JSONObject storage_settings, Server server, String type)
    {
        Storage storage = null;
        switch (type) {
            case "file":
                if (storage_settings == null) {
                    storage_settings = new JSONObject();
                    storage_settings.put("FILE_NAME", "employees.data");
                    storage_settings.put("DIRECTORY_NAME", "CompanyData");
                }
                storage = new PlainFileStorage(storage_settings, server);
                break;
            case "sql":
                storage = new SQLDatabaseStorage(storage_settings, server);
                break;
            case "nosql":
                break;
            default:
                throw new IllegalArgumentException("Unknown storage type: " + type);
        }

        return storage;
    }

    public static Storage createStorage(Server server, String type)
    {
        return createStorage(null, server, type);
    }
}

package telran.employees;

import telran.employees.storages.PlainFileStorage;

public class StorageFactory
{
    public static Storage createStorage(Server server, String type)
    {
        Storage storage = null;
        switch (type) {
            case "file":
                storage = new PlainFileStorage(server);
                break;
            case "sql":
                break;
            case "nosql":
                break;
            default:
                throw new IllegalArgumentException("Unknown storage type: " + type);
        }

        return storage;
    }
}

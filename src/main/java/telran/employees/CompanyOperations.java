package telran.employees;
import telran.employees.storages.PlainFile;
import telran.io.Persistable;
import telran.net.Protocol;
import telran.net.Response;
import telran.net.TCPServer;

import java.util.Arrays;

import static telran.net.ResponseCode.WRONG_DATA;
import static telran.net.ResponseCode.WRONG_REQUEST;

public class CompanyOperations implements Runnable
{
    private final Server server;

    private static final Protocol PROTOCOL = request -> {
        Response response;
        if (request.requestType() != null) {
            response = switch (request.requestType().toLowerCase()) {
/*
                case "echo" -> Controller.messageEcho(request.requestData());
                case "length" -> Controller.calculateLength(request.requestData());
                case "reverse" -> Controller.messageReverse(request.requestData());
*/
                default -> new Response(WRONG_REQUEST, String.format("Wrong request type: %s", request.requestType()));
            };
        } else {
            response = new Response(WRONG_DATA, "Wrong request data, null given");
        }
        return response;
    };

    public CompanyOperations(Server server) {
        this.server = server;
    }

    @Override
    public void run()
    {
        boolean running = true;
        if (server.getCompany() instanceof Persistable persistable_company) {
            try {
                if (server.getCompany() == null) {
                    server.setCompany(restoreCompany());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            while(running) {
                try {
                    TCPServer tcp_server = new TCPServer(PROTOCOL, server.PORT);
                    tcp_server.run();
                } catch (Exception e) {
                    System.out.println("Client closed connection abnormally");
                    System.err.println(e.getMessage() + " \n " + Arrays.toString(e.getStackTrace()));
                    running = false;
                }
            }

        }
    }

    private Company restoreCompany()
    {
        return new PlainFile().load();
    }
}

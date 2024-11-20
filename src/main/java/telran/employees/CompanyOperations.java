package telran.employees;
import telran.net.Protocol;
import telran.net.Response;
import telran.net.TCPServer;

import static telran.net.ResponseCode.WRONG_DATA;
import static telran.net.ResponseCode.WRONG_REQUEST;

public class CompanyOperations implements Runnable
{
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

    @Override
    public void run() {

    }
}

package fr.ensimag.controler;

public class Response {
    private boolean statusCode;
    private String response;

    public Response(boolean code, String response){
        setStatusCode(code);
        setResponse(response);
    }

    public void setStatusCode(boolean statusCode) {
        this.statusCode = statusCode;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean getStatusCode() {
        return statusCode;
    }

    public String getResponse() {
        return response;
    }
}

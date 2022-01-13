package ykvlv.lab4.data.dto;

public class Response<T> {
    private final String message;
    private final Boolean success;
    private final T object;

    public Response(String message, Boolean success, T object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public T getObject() {
        return object;
    }
}

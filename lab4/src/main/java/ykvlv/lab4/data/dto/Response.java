package ykvlv.lab4.data.dto;

import lombok.Value;

@Value
public class Response<T> {
    String message;
    boolean withObject;
    T object;
}

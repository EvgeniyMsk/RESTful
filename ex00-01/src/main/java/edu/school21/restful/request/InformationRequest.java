package edu.school21.restful.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InformationRequest {
    private int status;
    private String message;
}

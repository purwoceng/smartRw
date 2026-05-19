package com.codean.smart_rw.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefaultResponse {
    String result;
    String detail;
    int code ;
}

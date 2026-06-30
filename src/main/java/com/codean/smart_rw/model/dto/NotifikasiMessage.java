package com.codean.smart_rw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifikasiMessage implements Serializable {
    private String chatId;
    private String pesan;
    private String userId;
    private String jenis;
    private String pengumumanId;
    private String imageUrl;
}

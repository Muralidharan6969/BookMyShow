package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.PaymentProvider;
import com.example.bookmyshow.Models.ENUMS.PaymentStatus;
import com.example.bookmyshow.Models.ENUMS.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity (name = "PAYMENTS")
public class Payment extends BaseModel{
    private String refNo;
    private int totalAmount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;
}

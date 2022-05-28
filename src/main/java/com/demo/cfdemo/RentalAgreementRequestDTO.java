package com.demo.cfdemo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalAgreementRequestDTO {

    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

    private String toolCode;

    private int durationInDays;

    private int discountPercentage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "mm/dd/yy")
    private Date checkOutDate;

    public RentalAgreementRequestDTO(String toolCode, int durationInDays, int discountPercentage, String checkOutDate) throws ParseException {
        this.toolCode = toolCode;
        this.durationInDays = durationInDays;
        this.discountPercentage = discountPercentage;
        this.checkOutDate = formatter.parse(checkOutDate);
    }

    public String getToolCode() {
        return toolCode;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
}

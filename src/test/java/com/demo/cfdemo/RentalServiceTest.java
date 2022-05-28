package com.demo.cfdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RentalServiceTest {

    RentalService service = new RentalService();

    @BeforeAll
    public static void setUp() {
        CfDemoApplication.initializeData();
    }

    @Test
    public void processRental_OutOfRangeDuration_throwsException() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",0,20, "9/3/15");
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.processRental(request);
            });
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_OutOfRangeDiscount_throwsException() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",3,101, "9/3/15");
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.processRental(request);
            });
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_July4Holiday_countsAsUnchargedDay() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("CHNS",3,20, "7/3/16");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(2, result.getChargeDays());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_July4Holiday_observedHolidayAsUnchargedDay() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("LADW",5,20, "7/1/21");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(4, result.getChargeDays());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_laborDayHoliday_countsAsUnchargedDay() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("LADW",5,20, "9/6/20");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(4, result.getChargeDays());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_Test1() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",5,101, "9/3/15");
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.processRental(request);
            });
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_Test2() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("LADW",3,10, "7/2/20");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(2, result.getChargeDays());
            assertEquals(3.98, result.getPreDiscountCharge());
            assertEquals(.4, result.getDiscountAmount());
            assertEquals(3.58, result.getFinalCharge());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_Test3() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("CHNS",5,25, "7/2/15");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(3, result.getChargeDays());
            assertEquals(4.47, result.getPreDiscountCharge());
            assertEquals(1.12, result.getDiscountAmount());
            assertEquals(3.35, result.getFinalCharge());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_Test4() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKD",6,0, "9/3/15");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(3, result.getChargeDays());
            assertEquals(8.97, result.getPreDiscountCharge());
            assertEquals(0, result.getDiscountAmount());
            assertEquals(8.97, result.getFinalCharge());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_Test5() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",9,0, "7/2/15");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(6, result.getChargeDays());
            assertEquals(17.94, result.getPreDiscountCharge());
            assertEquals(0, result.getDiscountAmount());
            assertEquals(17.94, result.getFinalCharge());
        } catch (ParseException e){
            fail();
        }
    }

    @Test
    public void processRental_Test6() {
        try{
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",4,50, "7/2/20");
            RentalAgreementResponseDTO result = service.processRental(request);
            assertEquals(1, result.getChargeDays());
            assertEquals(2.99, result.getPreDiscountCharge());
            assertEquals(1.5, result.getDiscountAmount());
            assertEquals(1.49, result.getFinalCharge());
        } catch (ParseException e){
            fail();
        }
    }
}

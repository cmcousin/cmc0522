package com.demo.cfdemo;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class RentalService {

    public RentalAgreementResponseDTO processRental(RentalAgreementRequestDTO request) {
        //validate input
        if(request.getDurationInDays()<1){
            throw new IllegalArgumentException("Rental Days is not correct. Please select a value greater than or equal to 1.");
        }

        if(request.getDiscountPercentage()<0 || request.getDiscountPercentage()>100){
            throw new IllegalArgumentException("Discount percent is not correct. Please select a value in the range 0-100");
        }


        Tool refItem = CfDemoApplication.inventory.get(request.getToolCode());
        Date dueDate = calculateDueDate(request.getCheckOutDate(), request.getDurationInDays());
        int chargeDays = calculateChargeDays(refItem, request.getCheckOutDate(), dueDate, request.getDurationInDays());
        double preDiscountCharge = chargeDays * refItem.getDailyCharge();
        double discountAmount = new BigDecimal(request.getDiscountPercentage()*preDiscountCharge/100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        RentalAgreementResponseDTO result = new RentalAgreementResponseDTO(
                request.getToolCode(),
                refItem.getToolType(),
                refItem.getToolBrand(),
                request.getDurationInDays(),
                request.getCheckOutDate(),
                dueDate,
                refItem.getDailyCharge(),
                chargeDays,
                preDiscountCharge,
                request.getDiscountPercentage(),
                discountAmount,
                new BigDecimal(preDiscountCharge-discountAmount).setScale(2, RoundingMode.HALF_UP).doubleValue()
        );

        return result;
    }

    private Date calculateDueDate(Date checkOutDate, int durationInDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkOutDate);
        cal.add(Calendar.DAY_OF_YEAR, durationInDays);
        return cal.getTime();
    }

    private int calculateChargeDays(Tool refItem, Date checkOutDate, Date dueDate, int durationInDays){
        int unchargedDays = 0;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(checkOutDate);
        cal2.setTime(dueDate);

        if(!refItem.isWeekendCharge()){
            Calendar loopCal = cal1;
            while(loopCal.before(cal2)){
                if((Calendar.SATURDAY == loopCal.get(Calendar.DAY_OF_WEEK))
                ||(Calendar.SUNDAY == loopCal.get(Calendar.DAY_OF_WEEK))){
                    unchargedDays++;
                }
                loopCal.add(Calendar.DATE,1);
            }
        }
        if(!refItem.isHolidayCharge()){
            Date july4 = new GregorianCalendar(cal1.get(Calendar.YEAR),6, 4).getTime();
            Calendar j4Cal = Calendar.getInstance();
            j4Cal.setTime(july4);
            Date laborDay = java.sql.Date.valueOf(LocalDate.of(cal1.get(Calendar.YEAR), Month.SEPTEMBER, 1).with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY)));

            if(!(july4.before(checkOutDate) || july4.after(dueDate))){
                //if july4 falls on weekend, add as observed holiday
                unchargedDays++;
            }
            if(!(laborDay.before(checkOutDate) || laborDay.after(dueDate))){
                unchargedDays++;
            }
        }
        return durationInDays-unchargedDays;
    }
}

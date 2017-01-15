package com.goldentwo.utils;

import com.goldentwo.data.MyDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Calendar;
import java.util.Date;

public class DataAdapter extends XmlAdapter<MyDate, Date> {

    @Override
    public MyDate marshal(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return MyDate.builder()
                .dzien(calendar.get(Calendar.DAY_OF_MONTH))
                .miesiac(calendar.get(Calendar.MONTH)+1)
                .rok(calendar.get(Calendar.YEAR))
                .build();
    }

    @Override
    public Date unmarshal(MyDate myDate) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, myDate.getDzien());
        calendar.set(Calendar.MONTH, myDate.getMiesiac()-1);
        calendar.set(Calendar.YEAR, myDate.getRok());
        return calendar.getTime();
    }

}

package com.calendar;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: d.poberezhny
 * Date: 15.01.13
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class Record {
    private String mHead = "";
    private String mMessage = "";
    private Date mDateCreated = new Date();
    private Date mStartDate = new Date();
    private Date mEnd = null;


    public Record()
    {
        mDateCreated = new Date(System.currentTimeMillis());
    }


    public Record(String head, String message){
        mHead = head;
        mMessage = message;
        mDateCreated = new Date(System.currentTimeMillis());
    }

    public Record(String head, String message, Date start){
        mHead = head;
        mMessage = message;
        mDateCreated = new Date(System.currentTimeMillis());
        mStartDate = start;
    }

    public String toString() {
        String res =   mHead + "\n" +
                       mMessage + "\n" +
                       DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(mDateCreated) + "\n" +
                       DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(mStartDate)
                ;

        return res;

    }
    public String getHead(){
        return mHead;
    }

    public String getDateString(){
         return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(mDateCreated);
    }

    public Date getDate(){
        return mStartDate;
    }


    public String getMessage(){
        return mMessage;
    }

    public void setHead(String head){
        mHead = head;
    }

    public void setMessage(String message){
        mMessage = message;
    }
    public void setStartDate(Date startDate){
        mStartDate = startDate;
    }
}

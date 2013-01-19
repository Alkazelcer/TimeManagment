package com.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.calendar.dialogs.CalendarDialog;
import com.calendar.dialogs.RecordDialog;
import com.calendar.listeners.OnDateListener;
import com.calendar.listeners.OnRecordListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyActivity extends Activity
{
    TextView tvOut;
    Record mRecord;
    ListView mListView;

    ArrayList<Record> records = new ArrayList<Record>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mListView = (ListView) findViewById(R.id.recordList);


        records.add(new Record("first","-asdfasf",new Date(2006,1,27,11,4)));
        records.add(new Record("second","sadfadsf",new Date(2007,11,27,4,23)));
        records.add(new Record("third"," zxxcv",new Date(2008,5,3,5,32)));
        records.add(new Record("fourth","sdfgsdfgsdfgs", new Date(2003,1,24)));
        records.add(new Record("fifth","sdfgsdfgsdfgs", new Date()));

        findViewById(R.id.bNewEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GregorianCalendar calendar = new GregorianCalendar(2013, 3, 5);
                int i = (calendar.get(Calendar.DAY_OF_WEEK) + 2) % 6;
                mRecord = new Record("first", Integer.toString(i), new Date(2006, 4, 27));
                //             tvOut.setText(mRecord.toString());
            }
        });

        findViewById(R.id.bNewEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record record = new Record();
                RecordDialog eventDialog = new RecordDialog(MyActivity.this, new Record(),new OnRecordListener() {
                    @Override
                    public void OnRecordChanged(Record record) {
                        records.add(record);
                    }
                });
                eventDialog.show();
            }
        });

        findViewById(R.id.bCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
//                Date date = new Date(2003,1,24);
                CalendarDialog openImageDialog = new CalendarDialog(MyActivity.this, date, new OnDateListener() {
                    @Override
                    public void onDateChanged(Date date) {

                        ((TextView)findViewById(R.id.bDate)).setText(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
                    }
                });
                openImageDialog.show();
            }
        });
        final ListAdapter listAdapter = new ListAdapter();
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordDialog eventDialog = new RecordDialog(MyActivity.this, records.get(i),new OnRecordListener() {
                    @Override
                    public void OnRecordChanged(Record record) {
                        listAdapter.notifyDataSetChanged();
                    }
                });
                eventDialog.show();

            }
        });
        listAdapter.notifyDataSetChanged();
    }



    private class ListAdapter extends BaseAdapter {
            private LayoutInflater inflater;

            public ListAdapter() {
                inflater = MyActivity.this.getLayoutInflater();
            }

            public int getCount() {
                return records.size();
            }

            public Record getItem(int position) {
                return records.get(position);
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(int position, View item, ViewGroup parent) {
                ViewRecordHolder recordHolder;
                if (item == null) {
                    item = inflater.inflate(R.layout.record_string, parent, false);
                    item.setBackgroundColor((position % 2 == 0)? 0xFFCCFFFF: 0xFFCCFFCC);
                    recordHolder = new ViewRecordHolder();
                    recordHolder.Date = (TextView)item.findViewById(R.id.dateRecord);
                    recordHolder.Head =  (TextView)item.findViewById(R.id.headRecord);
                    recordHolder.Message = (TextView)item.findViewById(R.id.messageRecord);
                    item.setTag(recordHolder);
                } else {
                    recordHolder = (ViewRecordHolder)item.getTag();
                }
                String date = DateFormat.getTimeInstance(DateFormat.SHORT).format(getItem(position).getDate());
                recordHolder.Date.setText(date);
                recordHolder.Head.setText(getItem(position).getHead()+ " " +DateFormat.getDateInstance(DateFormat.SHORT).format(getItem(position).getDate()));
                recordHolder.Message.setText(getItem(position).getMessage());

                return item;
            }
        }
}

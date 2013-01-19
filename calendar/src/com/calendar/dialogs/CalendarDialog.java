package com.calendar.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.calendar.R;
import com.calendar.listeners.OnDateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: an.shevchenk
 * Date: 03.10.12
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class CalendarDialog extends Dialog {
        private GridView mGridView;
        private ArrayList<String> mDays = null;
        private TextView mMonthView , mYearView;
        private Button downMonth, upMonth;
        private Calendar mCalendar = Calendar.getInstance();
        private int mDayOfWeek;
        private Date mDateIn, mDateOut;
        private OnDateListener mOnDateListener;

        private String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        private int mMonth = 0;
        private int mYear;

//       private ArrayList<OnImageOpenListener> mListeners = new ArrayList<OnImageOpenListener>();


        public CalendarDialog(Context context, Date date, final OnDateListener onDateListener) {
            super(context);
            mOnDateListener = onDateListener;
            mDateIn = date;
            mDateOut = date;

            setContentView(R.layout.calendar_dialog);

            mYear = mDateIn.getYear();
            mMonth = mDateIn.getMonth();


            mGridView = (GridView) findViewById(R.id.open_image_grid_view);
            mMonthView = (TextView)findViewById(R.id.monthView);
            mYearView = (TextView)findViewById(R.id.yearView);
            mYearView.setText(Integer.toString(mYear));





            mMonthView.setText(month[mMonth]);

            init();

     //       mAdapter = new ListAdapter();
            mGridView.setAdapter( new ListAdapter());
        //    mAdapter.notifyDataSetChanged();


            downMonth = (Button)findViewById(R.id.monthDownButton);
            downMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( --mMonth == -1)
                    {
                        mYear--;
                        mMonth = 11;
                        mYearView.setText(Integer.toString(mYear));
                    }
                    mMonthView.setText(month[mMonth]);
                    init();
                }
            });

            upMonth = (Button)findViewById(R.id.monthUpButton);
            upMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( ++mMonth == 12)
                    {
                        mYear++;
                        mMonth = 0;
                        mYearView.setText(Integer.toString(mYear));
                    }
                    mMonthView.setText(month[mMonth]);
                    init();
                }
            });



        }

        private void init()
        {
            mDays = new ArrayList<String>(Arrays.asList("Su","Mn","Tue","Wd","Th","Fr","St"));

            mCalendar.set(Calendar.YEAR, mYear);
            mCalendar.set(Calendar.MONTH, mMonth);
            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
           // mCalendar.set(Calendar.DAY, 1);

            int maxDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            mDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

            for(int i = 0; i < mDayOfWeek - 1; ++i)
                mDays.add(" ");
            for(int i = 1; i < maxDay + 1; ++i)
                mDays.add(Integer.toString(i));
      //      mAdapter.notifyDataSetChanged();
            mGridView.setAdapter( new ListAdapter());
            mGridView.invalidateViews();
        }


    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public ListAdapter() {
                inflater = CalendarDialog.this.getLayoutInflater();
            }

        @Override
        public boolean isEnabled(int position) {
            if(position <  7 + mDayOfWeek - 1)
                return false;
            return true;
        }

        public int getCount() {
                return mDays.size();
        }

        public Object getItem(int position) {
                return mDays.get(position);
            }

        public long getItemId(int position) {
                return position;
            }


        public View getView(final int position, View item, ViewGroup parent) {
                LabelImageHolder holder;
                final int day = position - (7 + mDayOfWeek - 2 );
                if (item == null){
                    item = inflater.inflate(R.layout.calendar_dialog_grid_item,parent,false);
                    holder = new LabelImageHolder();
                    holder.label = (TextView)item.findViewById(R.id.tvGridView);
                    holder.imageView = item.findViewById(R.id.ivTodayDate);
                    item.setTag(holder);

                    if(day >= 0 ){
                        if( ( day + 2 == mDateIn.getDay())  && ( mMonth ==mDateIn.getMonth() )&& ( mYear == mDateIn.getYear() ) )
                        {
                            holder.imageView.setVisibility(View.VISIBLE);
                        }
                        else
                        if(holder.imageView.getVisibility() == View.VISIBLE)
                            holder.imageView.setVisibility(View.GONE);

                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //To change body of implemented methods use File | Settings | File Templates.

                                mDateOut.setYear(mYear);
                                mDateOut.setMonth(mMonth);
                                mDateOut.setDate(day);

                                mOnDateListener.onDateChanged(mDateOut);
                                hide();
                            }
                        });
                    }
                } else {
                    holder = (LabelImageHolder)item.getTag();
                }
                mCalendar.setTime(mDateIn);


                holder.label.setText((String)getItem(position));
                holder.label.setBackgroundColor((position % 2 == 0)? 0xFFCCFFFF: 0xFFCCFFCC);
                return item;
           }
           private class LabelImageHolder {
                public TextView label;
                public View imageView;
           }
        }



    }


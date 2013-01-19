package com.calendar.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.calendar.listeners.OnRecordListener;
import com.calendar.R;
import com.calendar.Record;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: d.poberezhny
 * Date: 17.01.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class RecordDialog extends Dialog{


    public RecordDialog(Context context,final Record record, final OnRecordListener onRecordListener) {
        super(context);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.event_dialog);

        final EditText etHead = (EditText)findViewById(R.id.etHeadEventDialog);
        final EditText etMessage = (EditText)findViewById(R.id.etMessageEventDialog);
        etHead.setText(record.getHead());
        etMessage.setText(record.getMessage());

          ((Button)findViewById(R.id.bOkRecordDialog)).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  record.setHead(etHead.getText().toString());
                  record.setMessage(etMessage.getText().toString());
                  record.setStartDate(new Date(2003, 3, 12, 3, 12));
                  onRecordListener.OnRecordChanged(record);
                  hide();
              }
          });

        ((Button)findViewById(R.id.bCancelRecordDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });



    }
}

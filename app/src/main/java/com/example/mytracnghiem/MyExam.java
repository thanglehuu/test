package com.example.mytracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyExam extends AppCompatActivity {
    TextView dscauhoi;
    Button themcauhoi;
    EditText cauhoi, cau_a, cau_b, cau_c, cau_d, dapan;
    quanlycauhoi db = new quanlycauhoi(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exam);

        matching();
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT);
        }

        Cursor cursor = db.laycauhoi();
        cursor.moveToFirst();
        String chuoi = "";
        List<cauhoi> ds_cauhoi = new ArrayList<cauhoi>();
        ds_cauhoi = db.layngaunhien(2);
        for(cauhoi x: ds_cauhoi) {
            chuoi += x.cauhoi + "\n";
        }
        dscauhoi.setText(chuoi);

        themcauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung = cauhoi.getText().toString();
                String a = cau_a.getText().toString();
                String b = cau_b.getText().toString();
                String c = cau_c.getText().toString();
                String d = cau_d.getText().toString();
                String da = dapan.getText().toString();

                if(noidung.isEmpty() && a.isEmpty() && b.isEmpty() && c.isEmpty() && d.isEmpty() && da.isEmpty()) {
                    Toast.makeText(MyExam.this, "Vui lòng nhập đẩy đủ các trường dữ liệu!", Toast.LENGTH_SHORT);
                    return;
                }

                db.addData(noidung, a, b, c, d, da);
                Toast.makeText(MyExam.this, "Thêm câu hỏi thành công", Toast.LENGTH_SHORT).show();
                cauhoi.setText("");
                cau_a.setText("");
                cau_b.setText("");
                cau_c.setText("");
                cau_d.setText("");
                dapan.setText("");
            }
        });
    }



    private void matching() {
        dscauhoi = (TextView) findViewById(R.id.textView);
        themcauhoi = (Button) findViewById(R.id.btn_add);
        cauhoi = (EditText) findViewById(R.id.et_cauhoi);
        cau_a = (EditText) findViewById(R.id.et_a);
        cau_b = (EditText) findViewById(R.id.et_b);
        cau_c = (EditText) findViewById(R.id.et_c);
        cau_d = (EditText) findViewById(R.id.et_d);
        dapan = (EditText) findViewById(R.id.et_dapan);
    }
}
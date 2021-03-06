package com.example.iclab.st;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

// 실측 액티비티(수목 실측): 지도에서 마커를 찍으면 넘어오는 화면
public class SurveyActivity extends AppCompatActivity {

    FrameLayout frame;
    ImageView point3, point4;
    EditText inputP3_1, inputP3_2, inputP3_3, inputP4_1, inputP4_2, inputP4_3, inputP4_4, inputTN;
    RadioGroup rg;
    int index = 0;
    CheckBox ckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // 위도 경도 좌표 값
        Intent preIntent = getIntent();

        final double latitude = preIntent.getDoubleExtra("latitude", 0.0f);
        final double longitude = preIntent.getDoubleExtra("longitude", 0.0f);

        Log.d("latitude", latitude + "");
        Log.d("longitude", longitude + "");

        Button nextBtn = (Button)findViewById(R.id.nextBtn);
        Button startBtn = (Button)findViewById(R.id.SurveyStart);
        Button rootBtn = (Button)findViewById(R.id.rootBtn);
        Button completeBtn = (Button)findViewById(R.id.completeBtn);
        Button modifyBtn = (Button)findViewById(R.id.modifyBtn);
        inputTN = (EditText)findViewById(R.id.inputTN);
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        frame = (FrameLayout)findViewById(R.id.frame);
        point3 = (ImageView)findViewById(R.id.point3);
        point4 = (ImageView)findViewById(R.id.point4);
        inputP3_1 = (EditText)findViewById(R.id.inputP3_1);
        inputP3_2 = (EditText)findViewById(R.id.inputP3_2);
        inputP3_3 = (EditText)findViewById(R.id.inputP3_3);
        inputP4_1 = (EditText)findViewById(R.id.inputP4_1);
        inputP4_2 = (EditText)findViewById(R.id.inputP4_2);
        inputP4_3 = (EditText)findViewById(R.id.inputP4_3);
        inputP4_4 = (EditText)findViewById(R.id.inputP4_4);
        ckBox = (CheckBox)findViewById(R.id.checkBox);

        changeView(index); // 실측화면 초기화

        // 체크박스 제어(수목번호 유무)
        ckBox.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(ckBox.isChecked()) { //없음이 체크되면
                    inputTN.setText(null); //기존에 입력된 내용 지우기
                    inputTN.setFocusableInTouchMode(false);
                    inputTN.setFocusable(false); // 입력창 비활성화
                }
                else { // 없음 체크 해제
                    inputTN.setFocusableInTouchMode(true);
                    inputTN.setFocusable(true); // 입력창 다시 활성화
                }
            }
        });

        // 라디오버튼 제어(설치전, 설치후)
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public  void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.beforeRadio) {
                    index = 1;
                }
                else if(i == R.id.afterRadio) {
                    index = 2;
                }
            }
        });

        // 실측시작 버튼 누르면 실측값 입력 화면 출력
        startBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                changeView(index);
            }
        });

        // 수정 버튼 누르면 보호판 선택 화면으로 전환
        modifyBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProtectpanelActivity.class);

                startActivity(intent);
            }
        });

        // 다음 버튼 누르면 맵 화면으로 전환
        nextBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);

                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);

                startActivity(intent);
                finish();
            }
        });

        // 수목뿌리 버튼 누르면 액티비티 전환
        rootBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RootActivity.class);

                startActivity(intent);
            }
        });

        // 실측완료 버튼 누르면 결과출력 화면으로 전환
        completeBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompleteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // 설치 전(3군데) - 설치 후(4군데)에 대한 view 전환
    public void changeView(int index) {
        switch (index) {
            case 0 :
                frame.removeView(inputP3_1);
                frame.removeView(inputP3_2);
                frame.removeView(inputP3_3);
                frame.removeView(inputP4_1);
                frame.removeView(inputP4_2);
                frame.removeView(inputP4_3);
                frame.removeView(inputP4_4);
                frame.removeView(point3);
                frame.removeView(point4);
                break;
            case 1 :
                frame.addView(point3);
                frame.addView(inputP3_1);
                frame.addView(inputP3_2);
                frame.addView(inputP3_3);
                frame.removeView(point4);
                frame.removeView(inputP4_1);
                frame.removeView(inputP4_2);
                frame.removeView(inputP4_3);
                frame.removeView(inputP4_4);
                break;
            case 2 :
                frame.addView(point4);
                frame.addView(inputP4_1);
                frame.addView(inputP4_2);
                frame.addView(inputP4_3);
                frame.addView(inputP4_4);
                frame.removeView(point3);
                frame.removeView(inputP3_1);
                frame.removeView(inputP3_2);
                frame.removeView(inputP3_3);
                break;
        }
    }
}

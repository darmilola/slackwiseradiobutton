package com.slackwise.example.slackwiseradiobutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.slackwise.slackwisebuttongroup.SlackWiseButton;
import com.slackwise.slackwisebuttongroup.SlackWiseButtonGroup;

public class MainActivity extends AppCompatActivity {

    SlackWiseButton codeButton;
    SlackWiseButton textButton;
    SlackWiseButton equationButton;
    SlackWiseButtonGroup buttonGroup;

    String code = " public static void main(String[] args) {\n" +
            "        int scores[] = {1,4,8,0};\n" +
            "        String classNames[] = {\"sola\",\"tolu\",\"dapo\",\"ayo\"};\n" +
            "        boolean found = false;\n" +
            "        Scanner input = new Scanner(System.in);\n" +
            "        System.out.println(\"Input the name you are looking for\");\n" +
            "        String nametofind = input.nextLine();\n" +
            "        \n" +
            "         for(int i = 0; i < classNames.length; i++){\n" +
            "             if(classNames[i].equalsIgnoreCase(nametofind)){\n" +
            "                 found = true;\n" +
            "                 System.out.println(\"I FOUND AYO AT INDEX\");\n" +
            "                 System.out.println(i);\n" +
            "                 break;\n" +
            "             }\n" +
            "          }\n" +
            "         if(found == false){\n" +
            "             System.out.println(\"I cant find what you are looking for\");\n" +
            "         }\n" +
            "         \n" +
            "    }\n" +
            "    \n" +
            "}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeButton = findViewById(R.id.code);
        equationButton = findViewById(R.id.equations);
        textButton = findViewById(R.id.text);
        buttonGroup = findViewById(R.id.button_group);
        buttonGroup.setGroupOrientation("vertical");


        buttonGroup.setOnCheckedChangeListener(new SlackWiseButtonGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {


                View mradioButton = buttonGroup.findViewById(checkedId);
                int index = buttonGroup.indexOfChild(mradioButton);

                Toast.makeText(MainActivity.this, Integer.toString(index), Toast.LENGTH_LONG).show();


            }
        });


        codeButton.setViewType("code");
        codeButton.setCodeLanguage("java");
        codeButton.setPadding(10f);
        codeButton.setSelectedBackground(R.color.colorPrimary);
        codeButton.setSourceCode(code);

        equationButton.setViewType("equation");
        equationButton.setPadding(10f);
        equationButton.setEquation("$\\int_0^\\infty e^{-x^2} dx=\\frac{\\sqrt{\\pi}}{2}$");
        equationButton.setEquationTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));
        equationButton.setEquationViewBackgroundColor(Color.parseColor("#00000000"));
        equationButton.setEquationTextSize(15f);
        equationButton.setSelectedBackground(R.drawable.create_course_edittext_background);




        textButton.setViewType("text");
        textButton.setPadding(15f);
        textButton.setTextSize(23f);
        textButton.setTextTypeFace(Typeface.MONOSPACE);
        textButton.setTextColor(Color.CYAN);
        textButton.setSelectedTextColor(Color.YELLOW);
        textButton.setSelectedBackground(R.drawable.create_course_edittext_background);
        textButton.setTextViewValue("Welcome to a new world yeah yeah");

    }
}

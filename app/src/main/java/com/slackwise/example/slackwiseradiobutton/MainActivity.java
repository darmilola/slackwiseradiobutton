package com.slackwise.example.slackwiseradiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.slackwise.slackwiseradiobutton.SlackWiseRadioButton;

public class MainActivity extends AppCompatActivity {

    SlackWiseRadioButton codeButton;

    String code = " public class SwapAlgoritm {\n" +
            "\n" +
            "    /**\n" +
            "     * @param args the command line arguments\n" +
            "     */\n" +
            "    public static void main(String[] args) {\n" +
            "        int a = 1;\n" +
            "        int b = 2;\n" +
            "        \n" +
            "        int temp = a;\n" +
            "        a = b;\n" +
            "        b = temp;\n" +
            "        \n" +
            "        System.out.println(b);\n" +
            "    }\n" +
            "    \n" +
            "    \n" +
            "}\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeButton = findViewById(R.id.slackwise_radio_button_code);
        codeButton.setSourceCode(code);

    }
}

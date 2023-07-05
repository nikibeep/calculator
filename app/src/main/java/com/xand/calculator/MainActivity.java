package com.xand.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayTextView;
    private StringBuilder currentNumber;
    private double result;
    private char operator;

    Button buttonBackspace;
    Button btnDecimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentNumber = new StringBuilder();
        result = 0;
        operator = ' ';

        displayTextView = findViewById(R.id.displayTextView);

        // Set click listeners for all calculator buttons
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnPercent).setOnClickListener(this);


        btnDecimal = findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumber.length() > 0 && !currentNumber.toString().contains(".")) {
                    currentNumber.append(".");
                    displayTextView.setText(currentNumber.toString());
                } else if (currentNumber.length() == 0) {
                    currentNumber.append("0.");
                    displayTextView.setText(currentNumber.toString());
                }
            }
        });


        buttonBackspace = findViewById(R.id.btnBackspace);
        buttonBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumber.length() > 0) {
                    currentNumber.deleteCharAt(currentNumber.length() - 1);
                    displayTextView.setText(currentNumber.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn0) {
            appendToNumber("0");
        } else if (id == R.id.btn1) {
            appendToNumber("1");
        } else if (id == R.id.btn2) {
            appendToNumber("2");
        } else if (id == R.id.btn3) {
            appendToNumber("3");
        } else if (id == R.id.btn4) {
            appendToNumber("4");
        } else if (id == R.id.btn5) {
            appendToNumber("5");
        } else if (id == R.id.btn6) {
            appendToNumber("6");
        } else if (id == R.id.btn7) {
            appendToNumber("7");
        } else if (id == R.id.btn8) {
            appendToNumber("8");
        } else if (id == R.id.btn9) {
            appendToNumber("9");
        } else if (id == R.id.btnAdd) {
            setOperator('+');
        } else if (id == R.id.btnSubtract) {
            setOperator('-');
        } else if (id == R.id.btnMultiply) {
            setOperator('*');
        } else if (id == R.id.btnDivide) {
            setOperator('/');
        } else if (id == R.id.btnEquals) {
            calculateResult();
        } else if (id == R.id.btnClear) {
            clearCalculator();
        } else if (id == R.id.btnPercent) {
            calculatePercentage();
        } else if (id == R.id.btnBackspace) {
            if (currentNumber.length() > 0) {
                currentNumber.deleteCharAt(currentNumber.length() - 1);
                displayTextView.setText(currentNumber.toString());
            }
        }
    }

    private void appendToNumber(String number) {
        if (number.equals(".")) {
            // Check if the current number already contains a decimal point
            if (!currentNumber.toString().contains(".")) {
                currentNumber.append(number);
            }
        } else {
            currentNumber.append(number);
        }
        displayTextView.setText(currentNumber.toString());
    }


    private void setOperator(char operator) {
        if (currentNumber.length() > 0) {
            try {
                result = Double.parseDouble(currentNumber.toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
                return;
            }
            currentNumber.setLength(0); // Clear the current number for the next input
            this.operator = operator;
        } else {
            Toast.makeText(this, "Enter a number first", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculatePercentage() {
        if (currentNumber.length() > 0) {
            double number;
            try {
                number = Double.parseDouble(currentNumber.toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
                return;
            }

            double percentage = number / 100.0;
            currentNumber.setLength(0); // Clear the current number for the next input
            currentNumber.append(String.valueOf(percentage));
            displayTextView.setText(currentNumber.toString());
        }
    }


    private void calculateResult() {
        if (currentNumber.length() > 0) {
            double secondOperand;
            try {
                secondOperand = Double.parseDouble(currentNumber.toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
                return;
            }

            switch (operator) {
                case '+':
                    result += secondOperand;
                    break;
                case '-':
                    result -= secondOperand;
                    break;
                case '*':
                    result *= secondOperand;
                    break;
                case '%':
                    result /= secondOperand;
                    result *= 100;
                    break;
                case '/':
                    if (secondOperand != 0) {
                        result /= secondOperand;
                    } else {
                        Toast.makeText(this, "Division by zero not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            }

            displayTextView.setText(String.valueOf(result));
            currentNumber.setLength(0); // Clear the current number for the next input
        }
    }

    private void clearCalculator() {
        currentNumber.setLength(0);
        result = 0;
        operator = ' ';
        displayTextView.setText("0");
    }
}

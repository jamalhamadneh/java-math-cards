

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MathPracticeApp extends JFrame implements ActionListener {

    private JTextField answerField, pointsField, questionField, timerField;
    private JLabel answerLabel, typesLable, factorLable, timerLable, timerLabel;
    ;
    private JCheckBox additionCheckBox, subtractionCheckBox, multiplicationCheckBox, divisionCheckBox;
    private JPanel panel1, panel2, panel3, panel4, panelf, panels, panelend;
    private JRadioButton random, zero, one, two, three, four, five, six, seven, egiht, nine, off, on_count_up, on_count_down;
    private JButton start, exit, stop, increaseTime, decreaseTime ;
    private Timer timer = new Timer(1000,this);
    private int seconds=0;
    private int minutes=0;
    private int countTime =0; // الوقت بالثواني
    private int countTime2;
    private int pointsCounter = 0;
    int triedQuestions = 0;
    int correctAnswers = 0;
    long startTime = 0;
    long endTime = 0;
    private long totalSolvingTime = 0;
    public MathPracticeApp() {
        super("Math Cards ");
        setLayout(new FlowLayout());

        // Initialize panel1
        panel1 = new JPanel(new GridLayout(5, 1));
        panel2 = new JPanel(new GridLayout(4, 3));
        panel3 = new JPanel(new GridLayout(7, 1));
        panel4 = new JPanel(new GridLayout(1, 3));
        panelf = new JPanel();
        panels = new JPanel();
        panelend = new JPanel();

        answerLabel = new JLabel(" Answer ");
        answerField = new JTextField(15);


        pointsField = new JTextField(10);

        panelf.add(answerLabel);
        panelf.add(answerField);
        panelf.add(pointsField);
        add(panelf);

        questionField = new JTextField(30);
        panels.add(questionField);
        add(panels);

        typesLable = new JLabel("Types : ");
        additionCheckBox = new JCheckBox(" Addition ");
        subtractionCheckBox = new JCheckBox(" Subtraction ");
        multiplicationCheckBox = new JCheckBox(" Multipication ");
        divisionCheckBox = new JCheckBox(" Division ");

        panel1.add(typesLable);
        panel1.add(additionCheckBox);
        panel1.add(subtractionCheckBox);
        panel1.add(multiplicationCheckBox);
        panel1.add(divisionCheckBox);

        add(panel1);

        factorLable = new JLabel("Factor :");
        random = new JRadioButton("Random");
        zero = new JRadioButton("0");
        one = new JRadioButton("1");
        two = new JRadioButton("2");
        three = new JRadioButton("3");
        four = new JRadioButton("4");
        five = new JRadioButton("5");
        six = new JRadioButton("6");
        seven = new JRadioButton("7");
        egiht = new JRadioButton("8");
        nine = new JRadioButton("9");

        panel2.add(factorLable);
        panel2.add(random);
        panel2.add(zero);
        panel2.add(one);
        panel2.add(two);
        panel2.add(three);
        panel2.add(four);
        panel2.add(five);
        panel2.add(six);
        panel2.add(seven);
        panel2.add(egiht);
        panel2.add(nine);

        add(panel2);

        timerLable = new JLabel("Timer :");
        off = new JRadioButton("Off");
        on_count_up = new JRadioButton("On Count_Up");
        on_count_down = new JRadioButton("On Count_Down");
        timerLabel =new JLabel("00:00");
        panel3.add(timerLable);
        panel3.add(off);
        panel3.add(on_count_up);
        panel3.add(on_count_down);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel3.add(timerLabel);

        increaseTime = new JButton("increaseTime");
        increaseTime.addActionListener(this);
        decreaseTime = new JButton("decreaseTime");
        decreaseTime.addActionListener(this);
        panel3.add(increaseTime);
        panel3.add(decreaseTime);

        off.addActionListener(this);
        on_count_up.addActionListener(this);
        on_count_down.addActionListener(this);


        RadioButtonHandler handleroff = new RadioButtonHandler();
        RadioButtonHandler handlerup = new RadioButtonHandler();
        RadioButtonHandler handlerdown = new RadioButtonHandler();
        off.addItemListener(handleroff);
        on_count_up.addItemListener(handlerup);
        on_count_down.addItemListener(handlerdown);


        add(panel3);
        panel4.add(panel1);
        panel4.add(panel2);
        panel4.add(panel3);
        add(panel4);

        start = new JButton(" Start Practice ");
        start.addActionListener(this);
        stop = new JButton(" Stop Practice ");
        stop.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        panelend.add(start);
        panelend.add(stop);
        panelend.add(exit);
        add(panelend);

        CheckBoxHandler handler = new CheckBoxHandler();
        additionCheckBox.addItemListener(handler);
        subtractionCheckBox.addItemListener(handler);
        multiplicationCheckBox.addItemListener(handler);
        divisionCheckBox.addItemListener(handler);

        RadioButtonHandler handlerr = new RadioButtonHandler();
        random.addItemListener(handlerr);
        zero.addItemListener(handlerr);
        one.addItemListener(handlerr);
        two.addItemListener(handlerr);
        three.addItemListener(handlerr);
        four.addItemListener(handlerr);
        five.addItemListener(handlerr);
        six.addItemListener(handlerr);
        seven.addItemListener(handlerr);
        egiht.addItemListener(handlerr);
        nine.addItemListener(handlerr);

        AnswerFieldListener answerFieldListener = new AnswerFieldListener();

        // Register the AnswerFieldListener with the answerField's Document
        answerField.getDocument().addDocumentListener(answerFieldListener);
        enableComponents(false);


    }

    private void displayResults() {
        double percentage = (correctAnswers / (double) triedQuestions) * 100;

        String resultMessage = "Results:\n" +
                "Total Questions: " + triedQuestions + "\n" +
                "Correct Answers: " + correctAnswers + "\n" +
                "Percentage Score: " + String.format("%.2f", percentage) + "%\n";

        if (startTime >= 0 ) {
            long elapsedTime = System.currentTimeMillis() - startTime ;
            long averageTimePerQuestion = elapsedTime / triedQuestions;

            resultMessage += "Total Time Spent: " + formatTime(elapsedTime) + "\n" +
                    "Average Time Per Question: " + formatTime(averageTimePerQuestion) + "\n";
        }

        JOptionPane.showMessageDialog(this, resultMessage, "Practice Results", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); // Exit the application
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    ///للبداية
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            enableComponents(true);
        } else if (e.getSource() == stop) {
            enableComponents(false);
            timer.stop();
        } else if (e.getSource() == exit) {
            displayResults(); //
        }else if (e.getSource() == increaseTime) {
            if (countTime < 20 * 60) { // زيادة الوقت حتى 20 دقيقة
                countTime += 10;
                countTime2=countTime;
                updateTimerLabel(minutes,seconds);
            }
        } else if (e.getSource() == decreaseTime) {
            if (countTime > 0) {
                countTime -= 10;
                countTime2=countTime;
                updateTimerLabel(minutes,seconds);
            }
        }
    }

    private void updateTimerLabel(int m,int s) {
        int minutes = countTime2 / 60;
        int seconds = countTime2 % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }


    private void enableComponents(boolean enable) {
        answerField.setEnabled(enable);
        additionCheckBox.setEnabled(enable);
        subtractionCheckBox.setEnabled(enable);
        multiplicationCheckBox.setEnabled(enable);
        divisionCheckBox.setEnabled(enable);
        random.setEnabled(enable);
        zero.setEnabled(enable);
        one.setEnabled(enable);
        two.setEnabled(enable);
        three.setEnabled(enable);
        four.setEnabled(enable);
        five.setEnabled(enable);
        six.setEnabled(enable);
        seven.setEnabled(enable);
        egiht.setEnabled(enable);
        nine.setEnabled(enable);
        off.setEnabled(enable);
        on_count_up.setEnabled(enable);
        on_count_down.setEnabled(enable);
        start.setEnabled(!enable);
        exit.setEnabled(!enable);
       // timerLabel.setEnabled(enable);
        //increaseTime.setEnabled(enable);
       // decreaseTime.setEnabled(enable);
    }

////////

    private String generateQuestionString(int num1, int factor, String operator) {
        return num1 + " " + operator + " " + factor;
    }

    private String generateQuestionString(int factor) {
        return String.valueOf(factor);
    }

    private class CheckBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            int num1 = new Random().nextInt(50);
            int factor = getSelectedFactor();

            String questionType = "";

            if (additionCheckBox.isSelected()) {
                questionType = generateQuestionString(num1, factor, "+") + " = ";
            } else if (subtractionCheckBox.isSelected()) {
                questionType = generateQuestionString(num1, factor, "-") + " = ";
            } else if (multiplicationCheckBox.isSelected()) {
                questionType = generateQuestionString(num1, factor, "*") + " = ";
            } else if (divisionCheckBox.isSelected()) {
                if (factor != 0) {
                    questionType = generateQuestionString(num1, factor, "/") + " = ";
                } else {
                    questionType = "Cannot divide by zero";
                }
            }

            questionField.setText(questionType);
        }
    }
    private int getSelectedFactor() {
        if (zero.isSelected()) {
            return 0;
        } else if (one.isSelected()) {
            return 1;
        } else if (two.isSelected()) {
            return 2;
        } else if (three.isSelected()) {
            return 3;
        } else if (four.isSelected()) {
            return 4;
        } else if (five.isSelected()) {
            return 5;
        } else if (six.isSelected()) {
            return 6;
        } else if (seven.isSelected()) {
            return 7;
        } else if (egiht.isSelected()) {
            return 8;
        } else if (nine.isSelected()) {
            return 9;
        } else {
            return new Random().nextInt(10);  // Default to random if none selected
        }
    }


    private class RadioButtonHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            int factor = 0;
            if (event.getSource() == zero) {
                factor = 0;
            } else if (event.getSource() == one) {
                factor = 1;
            } else if (event.getSource() == two) {
                factor = 2;
            } else if (event.getSource() == three) {
                factor = 3;
            } else if (event.getSource() == four) {
                factor = 4;
            } else if (event.getSource() == five) {
                factor = 5;
            } else if (event.getSource() == six) {
                factor = 6;
            } else if (event.getSource() == seven) {
                factor = 7;
            } else if (event.getSource() == egiht) {
                factor = 8;
            } else if (event.getSource() == nine) {
                factor = 9;
            } else if (event.getSource() == random) {
                factor = new Random().nextInt(10);
            }

            String questionString = generateQuestionString(factor);
            questionField.setText(questionString);

            if (timer != null) {
               timer.stop();
            }
            seconds = 0;
            minutes = 0;
            countTime2 = countTime; // إعادة تعيين الوقت للعمليات التنازلية
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds)); // أضف هذا السطر لتحديث عرض الوقت

            if (off.isSelected()) {
                timerLabel.setText("Off");
            }
            if (on_count_up.isSelected()) {
                 startTime = System.currentTimeMillis(); // Record the start time when practice begins
                minutes = countTime2 / 60;
                seconds = countTime2 % 60;
                timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        seconds++;
                        if (seconds == 60) {
                            seconds = 0;
                            minutes++;
                        }
                        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                        if (minutes == 20) {
                            timer.stop();
                            enableComponents(false);
                        }
                    }
                });
                timer.start();
            } else if (on_count_down.isSelected()) {
                 startTime = System.currentTimeMillis(); // Record the start time when practice begins

                minutes = countTime2 / 60;
                seconds = countTime2 % 60;
                timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        seconds--;
                        if (seconds == 0) {
                            if (minutes > 0) {
                                seconds = 59;
                              //  seconds--;
                                minutes--;
                            } else {
                                // Timer has reached 00:00, stop the timer
                                timer.stop();
                                enableComponents(false);
                            }
                        }
                        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                    }
                });
                timer.start();
            }

        }

    }

   private String calculateCorrectAnswer(int num1, int factor, String operator) {
        switch (operator) {
            case "+":
                return String.valueOf(num1 + factor);
            case "-":
                return String.valueOf(num1 - factor);
            case "*":
                return String.valueOf(num1 * factor);
            case "/":
                // Ensure factor is not zero to avoid division by zero
                return (factor != 0) ? String.valueOf(num1 / factor) : "Cannot divide by zero";
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private class AnswerFieldListener implements DocumentListener {
        private boolean answered = false;
        private boolean questionChanged = false; // تتبع ما إذا تغير السؤال
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (!answered) {
                checkAnswer();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            checkAnswer();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            checkAnswer();
        }

        private void checkAnswer() {
            String userAnswer = answerField.getText().trim();
            int userAnswerInt;
            try {
                userAnswerInt = Integer.parseInt(userAnswer);
            } catch (NumberFormatException ex) {
                return;
            }
            String[] questionParts = questionField.getText().split(" ");

            String correctAnswer = calculateCorrectAnswer(Integer.parseInt(questionParts[0]), Integer.parseInt(questionParts[2]), questionParts[1]);
            if (correctAnswer.length() == 2) {
                if (userAnswer.length() != 2) {
                    return;
                }
            } else if (correctAnswer.length() == 1) {
                if (userAnswer.length() != 1) {
                    return;
                }
            }else if (correctAnswer.length() == 3) {
                if (userAnswer.length() != 3) {
                    return;
                }
            }
            if (userAnswerInt == Integer.parseInt(correctAnswer)) {
                incrementPoints();
                questionChanged = true;
                incrementCorrectAnswers();
            } else {
                questionChanged = true;
            }

            generateNewQuestionIfRequired();
        }

        private String generateQuestionString(int num1, int factor, String operator) {
            return num1 + " " + operator + " " + factor;
        }

        private void incrementCorrectAnswers() {
            correctAnswers++;
        }
        private void incrementTotalQuestions() {
            triedQuestions++;
        }
        private void generateNewQuestionIfRequired() {
            if (questionChanged) {
                generateNewQuestion();
                questionChanged = false;
                incrementTotalQuestions();            }
        }

        private void generateNewQuestion() {
            int num1 = new Random().nextInt(50);
            int factor = getSelectedFactor();

            String questionType = "";

            if (additionCheckBox.isSelected()) {
                questionType = generateQuestionString(num1, factor, "+") + " = ";
            } else if (subtractionCheckBox.isSelected()) {
                questionType = generateQuestionString(num1, factor, "-") + " = ";
            } else if (multiplicationCheckBox.isSelected()) {
                questionType = generateQuestionString(num1, factor, "*") + " = ";
            } else if (divisionCheckBox.isSelected()) {
                if (factor != 0) {
                    questionType = generateQuestionString(num1, factor, "/") + " = ";
                } else {
                    questionType = "Cannot divide by zero";
                }
            }

            questionField.setText(questionType);
            answered = false; // إعادة ضبط الحالة للسماح بالإجابة على السؤال الجديد
        }

        private void incrementPoints() {
            pointsCounter++;
            pointsField.setText("Points: " + pointsCounter);
        }

    }
}





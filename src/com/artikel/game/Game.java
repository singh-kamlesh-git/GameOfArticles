package com.artikel.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Game extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel fileLabel;
	JButton fileButton;
	JButton start;
	JButton end;
	JLabel questionHeader;
	JLabel questionNoun;
	JLabel questionNounEnglish;
	JLabel error;
	JLabel score;
	JRadioButton rb1,rb2,rb3,rb4;
	JButton next;
	private ArtikelRow currentQuestion = new ArtikelRow();
	private static String lastFolder = "";
	static List<ArtikelRow> questions = new LinkedList<ArtikelRow>();
	private File selectedFile;
	private int questionAttempted=0;
	private int questionCorrect=0;
	private boolean isCurrentIncorrect=false;
	ButtonGroup G1;
	Game() {
		fileLabel = new JLabel("Choose File", SwingConstants.CENTER);
		fileLabel.setBounds(20, 20, 300, 20);
		add(fileLabel);
		fileButton = new JButton("Browse");
		fileButton.setBounds(350, 20, 100, 20);
		fileButton.addActionListener(this);
		add(fileButton);
		start = new JButton("Start");
		start.setBounds(110, 60, 100, 20);
		start.addActionListener(this);
		start.setVisible(false);
		add(start);
		end = new JButton("End");
		end.setBounds(220, 60, 100, 20);
		end.addActionListener(this);
		end.setVisible(false);
		add(end);
		
		G1 = new ButtonGroup(); 
		
		questionHeader = new JLabel("", SwingConstants.CENTER);
		questionHeader.setBounds(0, 120, 500, 30);
		questionHeader.setVisible(false);
		add(questionHeader);
		
		questionNoun = new JLabel("", SwingConstants.LEFT);
		questionNoun.setBounds(70, 170, 400, 30);
		questionNoun.setVisible(false);
		add(questionNoun);
		
		questionNounEnglish = new JLabel("", SwingConstants.LEFT);
		questionNounEnglish.setBounds(70, 200, 400, 30);
		questionNounEnglish.setVisible(false);
		add(questionNounEnglish);
		
		rb1=new JRadioButton("");    
		rb1.setBounds(50,250,450,30);      
		rb2=new JRadioButton("");    
		rb2.setBounds(50,280,450,30);
		rb3=new JRadioButton("");    
		rb3.setBounds(50,310,450,30);      
		rb4=new JRadioButton("");    
		rb4.setBounds(50,340,450,30);
		rb1.setVisible(false);
		rb2.setVisible(false);
		rb3.setVisible(false);
		rb4.setVisible(false);
		add(rb1);
		add(rb2);
		add(rb3);
		add(rb4);
		error = new JLabel("", SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setBounds(0, 380, 500, 30);
		error.setVisible(false);
		add(error);
		next = new JButton("Next");
		next.setBounds(300, 420, 100, 20);
		next.addActionListener(this);
		next.setVisible(false);
		add(next);
		G1.add(rb1);
		G1.add(rb2);
		G1.add(rb3);
		G1.add(rb4);
		score = new JLabel("", SwingConstants.CENTER);
		score.setForeground(Color.GREEN);
		score.setBounds(0, 450, 500, 30);
		score.setVisible(false);
		add(score);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileButton) {
			JFileChooser fc = new JFileChooser();
			if(!lastFolder.equalsIgnoreCase("")) {
				fc = new JFileChooser(lastFolder);
			}
			fc.setMultiSelectionEnabled(false);
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				selectedFile = f;
				resetQuestions(f);
				start.setVisible(true);
				lastFolder = fc.getSelectedFile().getParent();
				String filepath = f.getPath();
				fileLabel.setText(filepath);
			}
		}else if (e.getSource() == start) {
			if(start.getText().equalsIgnoreCase("restart"))
				resetQuestions(selectedFile);
			questionAttempted=0;
			questionCorrect=0;
			score.setVisible(false);
			error.setVisible(false);
			start.setText("Restart");
			end.setVisible(true);
			questionHeader.setVisible(true);
			ArtikelRow question = getRandomQuestion();
			currentQuestion = question;
			questionHeader.setText(question.getQuestionAction());
			questionNoun.setText(questionAttempted+1+") "+question.getQuestion());
			questionNoun.setVisible(true);
			questionNounEnglish.setText("    "+question.getQuestionEnglish());
			questionNounEnglish.setVisible(true);
			List<String> options = question.getOptions();
			if(options.get(0)!=null) {
				rb1.setText("A) "+options.get(0));
				rb1.setVisible(true);
			}
			if(options.size()>1 && options.get(1)!=null) {
				rb2.setText("B) "+options.get(1));
				rb2.setVisible(true);
			}
			if(options.size()>2 && options.get(2)!=null) {
				rb3.setText("C) "+options.get(2));
				rb3.setVisible(true);
			}
			if(options.size()>3 && options.get(3)!=null) {
				rb4.setText("D) "+options.get(3));	
				rb4.setVisible(true);
			}
			next.setVisible(true);
		}else if (e.getSource() == end) {
			start.setText("Start");
			end.setVisible(false);
			questionAttempted=0;
			questionCorrect=0;
			score.setVisible(false);
			questionHeader.setVisible(false);
			questionNoun.setVisible(false);
			questionNounEnglish.setVisible(false);
			rb1.setVisible(false);
			rb2.setVisible(false);
			rb3.setVisible(false);
			rb4.setVisible(false);
			next.setVisible(false);
			error.setVisible(false);
		}else if (e.getSource() == next) {
			boolean isCorrect=false;
			error.setVisible(false);
			if (rb1.isSelected()) {
				isCorrect = rb1.getText().substring(rb1.getText().indexOf(')')+2).trim().equalsIgnoreCase(currentQuestion.getAnswer().trim());
			}
			else if (rb2.isSelected()) {
				isCorrect = rb2.getText().substring(rb2.getText().indexOf(')')+2).trim().equalsIgnoreCase(currentQuestion.getAnswer().trim());
			}
			else if (rb3.isSelected()) {
				isCorrect = rb3.getText().substring(rb3.getText().indexOf(')')+2).trim().equalsIgnoreCase(currentQuestion.getAnswer().trim());
			}
			else if (rb4.isSelected()) {
				isCorrect = rb4.getText().substring(rb4.getText().indexOf(')')+2).trim().equalsIgnoreCase(currentQuestion.getAnswer().trim());
			}else {
				error.setText("Please select an option");
				error.setVisible(true);
				return;
			} 
			if(!isCorrect) {
				error.setText("Please select a correct option");
				isCurrentIncorrect = true;
				error.setVisible(true);
				return;
			}else {
				if(!isCurrentIncorrect)
					questionCorrect++;
				questionAttempted++;
				score.setText("Score: "+questionCorrect+"/"+questionAttempted);
				score.setVisible(true);
				drawNextQuestion();
				isCurrentIncorrect=false;
			}
			
			rb1.setSelected(false);
			rb2.setSelected(false);
			rb3.setSelected(false);
			rb4.setSelected(false);
		}
		
	}

	public static void main(String[] args) {
		Game om = new Game();
		om.setSize(550, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		om.setLocation(dim.width/2-om.getSize().width/2, dim.height/2-om.getSize().height/2);
		om.setLayout(null);
		om.setVisible(true);
		om.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void resetQuestions(File f){
		XSSFWorkbook workbook = null; 
		questions = new LinkedList<ArtikelRow>();
		try {
			workbook = new XSSFWorkbook(f);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
	            Cell cell1 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            Cell cell2 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            Cell cell3 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            Cell cell4 = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            Cell cell5 = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            String value1 = cell1==null?"":cell1.getStringCellValue();
	            String value2 = cell2==null?"":cell2.getStringCellValue();
	            String value3 = cell3==null?"":cell3.getStringCellValue();
	            String value4 = cell4==null?"":cell4.getStringCellValue();
	            String value5 = cell5==null?"":cell5.getStringCellValue();
	            ArtikelRow aRow = new ArtikelRow();
	            aRow.setQuestionAction(value1);
	            aRow.setQuestion(value2);
	            aRow.setQuestionEnglish(value3);
	            aRow.setOptions(Arrays.asList(value4.split(";")));
	            aRow.setAnswer(value5);
	            if(aRow.getQuestion() == null || aRow.getQuestion().equalsIgnoreCase("")) {
	            	break;
	            }
	            questions.add(aRow);
			}
			
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			try {
				if(workbook!=null)
					workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private ArtikelRow getRandomQuestion() {
		if (questions.size()==0) {
			return null;
		}
		Random rand = new Random();
		int randomIndex = rand.nextInt(questions.size());
		ArtikelRow randomElement = questions.get(randomIndex);
        questions.remove(randomIndex);
        return randomElement;
	}
	private void drawNextQuestion() {
		ArtikelRow question = getRandomQuestion();
		if(question==null) {
			error.setText("End of the questions");
			error.setVisible(true);
			next.setVisible(false);
			return;
		}
		rb1.setVisible(false);
		rb2.setVisible(false);
		rb3.setVisible(false);
		rb4.setVisible(false);
		G1.clearSelection();
		
		currentQuestion = question;
		questionHeader.setText(question.getQuestionAction());
		questionNoun.setText(questionAttempted+") "+question.getQuestion());
		questionNounEnglish.setText("    "+question.getQuestionEnglish());
		List<String> options = question.getOptions();
		if(options.get(0)!=null) {
			rb1.setText("A) "+options.get(0));
			rb1.setVisible(true);
		}
		if(options.size()>1 && options.get(1)!=null) {
			rb2.setText("B) "+options.get(1));
			rb2.setVisible(true);
		}
		if(options.size()>2 && options.get(2)!=null) {
			rb3.setText("C) "+options.get(2));
			rb3.setVisible(true);
		}
		if(options.size()>3 && options.get(3)!=null) {
			rb4.setText("D) "+options.get(3));	
			rb4.setVisible(true);
		}
	}
}

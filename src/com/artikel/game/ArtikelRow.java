package com.artikel.game;

import java.util.List;

public class ArtikelRow {

	private String questionAction;
	private String question;
	private String questionEnglish;
	private String answer;
	private List<String> options;
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<String> getOptions() {
		return options;
	}
	public void addOptions(String option) {
		if(option!=null && !option.equalsIgnoreCase(""))
			this.options.add(option);
	}
	public String getQuestionEnglish() {
		return questionEnglish;
	}
	public void setQuestionEnglish(String questionEnglish) {
		this.questionEnglish = questionEnglish;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
            return true; 
        } 
        if (!(o instanceof ArtikelRow)) { 
            return false; 
        } 
        ArtikelRow c = (ArtikelRow) o; 
        return question.equalsIgnoreCase(c.question); 
	}
	public String getQuestionAction() {
		return questionAction;
	}
	public void setQuestionAction(String questionAction) {
		this.questionAction = questionAction;
	}
}

package com.example.mosishwapp.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class QuestionDTO implements Serializable {

	@SerializedName("questionId")
	private String questionId;

	@SerializedName("question")
	private String question;

	@SerializedName("answers")
	private List<String> answers;

	@SerializedName("correct_answer")
	private String correctAnswer;

	@SerializedName("position")
	private List<String> position;

	@Override
 	public String toString(){
		return "{" +
			"questionId = '" + questionId + '\'' +
			",question = '" + question + '\'' +
			",answers = '" + answers + '\'' + 
			",correct_answer = '" + correctAnswer + '\'' + 
			",position = '" + position + '\'' + "}";
		}


	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public void setPosition(List<String> position) {
		this.position = position;
	}

	public String toJsonObject() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static QuestionDTO fromJsonObject(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, QuestionDTO.class);
	}
}
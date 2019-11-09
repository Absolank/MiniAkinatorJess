package com.iiita.controllers;

import com.iiita.Magi;
import com.iiita.api.QuestionEngine;
import com.iiita.models.HarryPotterCharacter;
import com.iiita.models.QuestionItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MagiController {
	@FXML
	private Label question;
	private Magi app;
	private QuestionEngine questionEngine;
	private QuestionItem questionItem;

	public void setQuestionEngine(QuestionEngine questionEngine) {
		this.questionEngine = questionEngine;
	}
	private void setNextQuestion() throws Exception {
		questionItem = questionEngine.getNextQuestion();
	}
	public void start() throws Exception {
		setNextQuestion();
		question.setText(questionItem.toString());
	}
	public void setApp(Magi stage) {
		this.app = stage;
	}
	@FXML
	void onClose(ActionEvent event)  {
		if(app != null){
			app.close();
		}
	}
	private void answerQuestion(boolean b) throws Exception {
		if(questionItem != null) {
			questionEngine.pushAnswer(b);
			setNextQuestion();
			if (questionItem != null) {
				System.out.println(questionItem.toString());
				question.setText(questionItem.toString());
			} else {
				HarryPotterCharacter answer = questionEngine.getAnswer();
				app.showAnswerScene(answer);
			}
		}
		else{
			app.showAnswerScene(null);
		}
	}
	@FXML
	void onNo(ActionEvent event) throws Exception {
		answerQuestion(false);
	}

	@FXML
	void onYes(ActionEvent event) throws Exception {
		answerQuestion(true);
	}
}

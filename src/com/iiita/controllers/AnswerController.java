package com.iiita.controllers;

import com.iiita.models.HarryPotterCharacter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class AnswerController {

	@FXML
	private Label answer;
	@FXML
	private Button close;

	private static String cannotFindTheCharacter = "It seems I cannot guess the character.";

	@FXML
	public void initialize() {
		answer.setText(cannotFindTheCharacter);
	}

	public void setAnswer(HarryPotterCharacter harryPotterCharacter){
		String characterFound = "Here is the guess.               ";
		if(harryPotterCharacter != null)
			answer.setText(characterFound + harryPotterCharacter.getName());
		else
			answer.setText(cannotFindTheCharacter);
	}
	public void onClose(Stage stage)
	{
		close.setOnAction(e-> stage.close());

	}
}
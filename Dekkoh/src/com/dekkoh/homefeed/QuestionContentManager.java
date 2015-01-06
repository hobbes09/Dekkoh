package com.dekkoh.homefeed;

import java.util.List;

import com.dekkoh.application.ApplicationState;
import com.dekkoh.datamodel.Question;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.FileManager;
import com.dekkoh.util.Log;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class QuestionContentManager implements Runnable{
	
	public static boolean updateSuccessful = false;

	public static void fetchQuestionsFromBackend() throws Exception{
		List<Question> newQuestionList = APIProcessor.getQuestions((Activity)HomeScreen.homeScreenContext, ApplicationState.getHomefeedQuestion_Offset(), ApplicationState.getHomefeedQuestion_Limit(), 0, 0, null, null, 0, null, false, null, null, null);
		List<Question> existingQuestionList;
		if(newQuestionList != null){
			ApplicationState.updateOffset();
			if(FileManager.getInstance().isObjectFileExistsInExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile())){
				existingQuestionList = (List<Question>)FileManager.getInstance().readObjectFileFromExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile());
				existingQuestionList.addAll(newQuestionList);
				FileManager.getInstance().writeObjectFileInExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile(), existingQuestionList);
			}else{
				FileManager.getInstance().writeObjectFileInExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile(), newQuestionList);
			}
			updateSuccessful = true;
		}
		Log.e("Can't fetch question from Backend");
		updateSuccessful = false;
	}
	
	public static Question getQuestionFromExternalStorage() throws Exception{
		List<Question> existingQuestionList = null;
		Question question = null;
		if(FileManager.getInstance().isObjectFileExistsInExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile())){
			existingQuestionList = (List<Question>)FileManager.getInstance().readObjectFileFromExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile());
			question = existingQuestionList.get(ApplicationState.getHomefeedQuestion_CurrentIndex());
			return question;
		}else{
			Thread threadQuestionUpdater = new Thread("Question Updater");
			threadQuestionUpdater.start();
			threadQuestionUpdater.join();
			return getQuestionFromExternalStorage();
		}	
	}
	
	public static Bundle getNextQuestionContent() throws Exception{
		ApplicationState.setHomefeedQuestion_CurrentIndex(ApplicationState.getHomefeedQuestion_CurrentIndex() + 1);
		Question question = getQuestionFromExternalStorage();
		Bundle questionFragArgs = new Bundle();
		questionFragArgs.putString("LOCATION", question.getLocation());
		questionFragArgs.putString("USERNAME", question.getUserName());
		questionFragArgs.putString("QUESTION", question.getQuestion());
		return questionFragArgs;
	}
	
	public static Bundle getPreviousQuestionContent() throws Exception{
		ApplicationState.setHomefeedQuestion_CurrentIndex(ApplicationState.getHomefeedQuestion_CurrentIndex() - 1);
		Question question = getQuestionFromExternalStorage();
		Bundle questionFragArgs = new Bundle();
		questionFragArgs.putString("LOCATION", question.getLocation());
		questionFragArgs.putString("USERNAME", question.getUserName());
		questionFragArgs.putString("QUESTION", question.getQuestion());
		return questionFragArgs;
	}

	@Override
	public void run() {
		try {
			fetchQuestionsFromBackend();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
	

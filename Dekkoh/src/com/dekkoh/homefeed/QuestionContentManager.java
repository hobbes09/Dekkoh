package com.dekkoh.homefeed;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.dekkoh.application.ApplicationState;
import com.dekkoh.datamodel.Question;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.FileManager;
import com.dekkoh.util.Log;

public class QuestionContentManager{
	
public static boolean updateSuccessful = false;
	
	// Temporary variable
	private static List<Question> questionList = null; 
	private static int currentIndex = -1;
	
	private static QuestionContentManager questionContentManager = new QuestionContentManager();
	
	private QuestionContentManager(){		
	}
	
	public static QuestionContentManager getInstance(){
		return questionContentManager;
	}

	public static List<Question> getQuestionList() {
		return questionList;
	}

	public static void setQuestionList(List<Question> questionList) {
		QuestionContentManager.questionList = questionList;
	}

	public static int getCurrentIndex() {
		return currentIndex;
	}

	public static void setCurrentIndex(int currentIndex) {
		QuestionContentManager.currentIndex = currentIndex;
	}

	public static void fetchQuestionsFromBackend() throws Exception{
		updateSuccessful = false;
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
	}
	
	public static Question getQuestionFromExternalStorage() throws Exception{
		List<Question> existingQuestionList = null;
		Question question = null;
		if(FileManager.getInstance().isObjectFileExistsInExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile())){
			existingQuestionList = (List<Question>)FileManager.getInstance().readObjectFileFromExternalStorage((Activity)HomeScreen.homeScreenContext, ApplicationState.getQuestionsfile());
			question = existingQuestionList.get(ApplicationState.getHomefeedQuestion_CurrentIndex());
			return question;
		}
		return null;
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
	
	public static Bundle getNextQuestionBundle(Activity activity){
		QuestionContentManager.getInstance().setCurrentIndex(QuestionContentManager.getInstance().getCurrentIndex() + 1);
		Question question = QuestionContentManager.getInstance().getQuestionList().get(QuestionContentManager.getInstance().getCurrentIndex());
		Bundle questionFragArgs = new Bundle();
		questionFragArgs.putString("LOCATION", question.getLocation());
		questionFragArgs.putString("USERNAME", question.getUserName());
		questionFragArgs.putString("QUESTION", question.getQuestion());
		questionFragArgs.putString("NUM_ANSWERS", Integer.toString(question.getAnswerCount()));
		questionFragArgs.putString("PROFILE_PIC", question.getUserImage());
		questionFragArgs.putString("QUESTION_PIC", question.getImage());
		return questionFragArgs;
	}
	
	public static Bundle getPreviousQuestionBundle(Activity activity){	
		QuestionContentManager.getInstance().setCurrentIndex(QuestionContentManager.getInstance().getCurrentIndex() - 1);
		Question question = QuestionContentManager.getInstance().getQuestionList().get(QuestionContentManager.getInstance().getCurrentIndex());
		Bundle questionFragArgs = new Bundle();
		questionFragArgs.putString("LOCATION", question.getLocation());
		questionFragArgs.putString("USERNAME", question.getUserName());
		questionFragArgs.putString("QUESTION", question.getQuestion());
		questionFragArgs.putString("NUM_ANSWERS", Integer.toString(question.getAnswerCount()));
		questionFragArgs.putString("PROFILE_PIC", question.getUserImage());
		questionFragArgs.putString("QUESTION_PIC", question.getImage());
		return questionFragArgs;
	}
	
	public static Question getNextQuestion(){
		QuestionContentManager.getInstance().setCurrentIndex(QuestionContentManager.getInstance().getCurrentIndex() + 1);
		Question question = QuestionContentManager.getInstance().getQuestionList().get(QuestionContentManager.getInstance().getCurrentIndex());
		return question;
	}
	
	public static Question getPreviousQuestion(){
		QuestionContentManager.getInstance().setCurrentIndex(QuestionContentManager.getInstance().getCurrentIndex() - 1);
		Question question = QuestionContentManager.getInstance().getQuestionList().get(QuestionContentManager.getInstance().getCurrentIndex());
		return question;
	}
	
}
	

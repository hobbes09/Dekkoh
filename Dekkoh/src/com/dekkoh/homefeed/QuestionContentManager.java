package com.dekkoh.homefeed;

import android.os.Bundle;

public class QuestionContentManager {

	private static int counter = -1;
	
	public static Bundle setNextQuestion(){
		counter++;
		Bundle questionFragArgs = new Bundle();
		questionFragArgs.putString("LOCATION", "Location"+counter);
		questionFragArgs.putString("USERNAME", "Username"+counter);
		questionFragArgs.putString("QUESTION", "Question" + counter);
		return questionFragArgs;
	}
}

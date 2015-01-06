package com.dekkoh.application;

public class ApplicationState {
	
	private static int homefeedQuestion_Offset = 1;
	private static int homefeedQuestion_Limit = 5;
	private static int homefeedQuestion_CurrentIndex = -1;
	

	public static void updateOffset(){
		setHomefeedQuestion_Offset(getHomefeedQuestion_Offset() + getHomefeedQuestion_Limit());
	}
	
	public static int getHomefeedQuestion_Offset() {
		return homefeedQuestion_Offset;
	}
	public static void setHomefeedQuestion_Offset(int homefeedQuestion_Offset) {
		ApplicationState.homefeedQuestion_Offset = homefeedQuestion_Offset;
	}
	public static int getHomefeedQuestion_Limit() {
		return homefeedQuestion_Limit;
	}
	public static void setHomefeedQuestion_Limit(int homefeedQuestion_Limit) {
		ApplicationState.homefeedQuestion_Limit = homefeedQuestion_Limit;
	}
	public static int getHomefeedQuestion_CurrentIndex() {
		return homefeedQuestion_CurrentIndex;
	}
	public static void setHomefeedQuestion_CurrentIndex(
			int homefeedQuestion_CurrentIndex) {
		ApplicationState.homefeedQuestion_CurrentIndex = homefeedQuestion_CurrentIndex;
	}
	

}

package com.dekkoh.application;

import android.app.Application;
import android.location.Location;

import com.dekkoh.datamodel.DekkohUser;

public class DekkohApplication extends Application {

	// Location Parameters
	private double locationLatitude = 0.00;
	private double locationLongitude = 0.00;

	private DekkohUser dekkohUser;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	// Updated location
	public void updateLocationOfUser(Location location) {
		// TODO: Use these Lati n longi to store on server.
	//	Toast.makeText(
	//			getApplicationContext(),
	//			"Lati: " + location.getLatitude() + "\n" + "Longi: "
	//					+ location.getLongitude(), Toast.LENGTH_SHORT).show();
		setLocationLatitude(location.getLatitude());
		setLocationLongitude(location.getLongitude());
	}

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	/**
	 * @return the dekkohUser
	 */
	public DekkohUser getDekkohUser() {
		return dekkohUser;
	}

	/**
	 * @param dekkohUser
	 *            the dekkohUser to set
	 */
	public void setDekkohUser(DekkohUser dekkohUser) {
		this.dekkohUser = dekkohUser;
	}

}

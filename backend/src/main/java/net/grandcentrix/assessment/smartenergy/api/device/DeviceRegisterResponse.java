package net.grandcentrix.assessment.smartenergy.api.device;

public class DeviceRegisterResponse {

	String password;

	public DeviceRegisterResponse() {
	}

	public DeviceRegisterResponse(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

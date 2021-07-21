package net.grandcentrix.assessment.smartenergy.api.device;

public class DeviceRegisterRequest {

	private String macAddress;

	public DeviceRegisterRequest() {
	}

	public DeviceRegisterRequest(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}
}

package com.bct.addon.dxch.rest.json.response;

import com.bct.scad.mdm.common.rest.json.response.*;

public class PersonResponse {
	private AcceptedRecords acceptedRecords;
	private RejectedRecords rejectedRecords;
	private ErrorMessageRecords errorMessageRecords;

	public AcceptedRecords getAcceptedRecords() {
		return this.acceptedRecords;
	}

	public void setCreatedRecords(final AcceptedRecords pAcceptedRecords) {
		this.acceptedRecords = pAcceptedRecords;
	}

	public RejectedRecords getRejectedRecords() {
		return this.rejectedRecords;
	}

	public void setRejectedRecords(final RejectedRecords rejectedRecords) {
		this.rejectedRecords = rejectedRecords;
	}

	public ErrorMessageRecords getErrorMessageRecords() {
		return this.errorMessageRecords;
	}

	public void setErrorMessageRecords(final ErrorMessageRecords errorMessageRecords) {
		this.errorMessageRecords = errorMessageRecords;
	}

	public void addAcceptedRecord(final AcceptedResponse record) {
		if (this.acceptedRecords == null) {
			this.acceptedRecords = new AcceptedRecords();
		}

		this.acceptedRecords.addAcceptedRecord(record);
	}

	public void addRejectedRecord(final RejectedResponse rejectedResponse) {
		if (this.rejectedRecords == null) {
			this.rejectedRecords = new RejectedRecords();
		}

		this.rejectedRecords.addAcceptedRecord(rejectedResponse);

	}
}
var DINT_GLOBAL_TEMPLATE_VARS = DINT_GLOBAL_TEMPLATE_VARS || {};

const TemplateAction = {
	SAVE: 'SAVE',
	UPDATE: 'UPDATE',
}

function showConfirmPopup() {
	doYesAction = function () {
		ebx_dialogBox_hide();
		updateOrSaveTemplateAndProcess();
	};
	doNoAction = function () {
		ebx_dialogBox_hide();
		nonConfirmJs();
	};
	showConfirmDialog(DINT_GLOBAL_TEMPLATE_VARS.confirmQuestion, DINT_GLOBAL_TEMPLATE_VARS.closeButtonTooltip,
		DINT_GLOBAL_TEMPLATE_VARS.yesButtonLabel,
		DINT_GLOBAL_TEMPLATE_VARS.noButtonLabel, 'doYesAction', 'doNoAction');
}

function updateOrSaveTemplateAndProcess() {
	if (DINT_GLOBAL_TEMPLATE_VARS.userTemplateUuid) {
		openPopupUserTemplate(TemplateAction.UPDATE);
	} else {
		openPopupUserTemplate(TemplateAction.SAVE);
	}
};

function openPopupUserTemplate(action) {
	EBX_Utils.openInternalPopup(DINT_GLOBAL_TEMPLATE_VARS.templateServiceUri + '&action=' + action, 1030, 736,
		{ isCloseButtonDisplayed: true, isResizable: false, isCloseButtonOnPopupEdge: false });
}

function startPersistTemplate() {
	EBX_Utils.closeInternalPopup();

	if (DINT_GLOBAL_TEMPLATE_VARS.userTemplateUuid) {
		handle_success_response_dint = onSuccessUpdateTemplateJs;
		handle_error_response_dint = onFailedUpdateTemplateJs;
		updateUserTemplate(DINT_GLOBAL_TEMPLATE_VARS.userTemplateUuid);
		return;
	}
	handle_success_response_dint = onSuccessSaveTemplateJs;
	handle_error_response_dint = onFailedSaveTemplateJs;
	saveUserTemplate();
};

function updateUserTemplate(templateUuid) {
	var url = DINT_GLOBAL_TEMPLATE_VARS.dintUpdateurl;
	url = url + '&userTemplateUuid=' + templateUuid;
	console.log(`Update User Template`);
	var params = DINT_GLOBAL_TEMPLATE_VARS.jsonMapping;
	sendPOSTRequest(url, params, handle_response_dint);
}

function saveUserTemplate() {
	var url = DINT_GLOBAL_TEMPLATE_VARS.dintSaveurl;
	console.log(`Save User Template`);
	var params = DINT_GLOBAL_TEMPLATE_VARS.jsonMapping;
	sendPOSTRequest(url, params, handle_response_dint);
}

function sendPOSTRequest(url, params, handleResponseFunction) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState == 4 && this.status == 200) {
			handleResponseFunction(this);
		}
	};
	xhttp.open('POST', url, true);
	xhttp.setRequestHeader('Content-type', 'application/json');
	xhttp.send(params);
}

function handle_response_dint(resObj) {
	var data = JSON.parse(resObj.response);

	if (data.severity === 'ERROR') {
		handle_error_response_dint(data);
		return;
	}
	handle_success_response_dint(data);
}



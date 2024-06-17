/*
 * Global mapping variable defined at back-end: DINT_GLOBAL_MAPPING_VARS
 */

// FIXME: set prefix for functions
const mappingFrameID = '_mapping_frame_id';
const SAVE_AS_TEMPLATE_BUTTON_ID = 'SAVE_AS_TEMPLATE_BUTTON_ID';
const HIDDEN_CANCEL_BUTTON_ID = 'HIDDEN_CANCEL_BUTTON_ID';

const TemplateAction = {
	SAVE: 'SAVE',
	SAVEAS: 'SAVEAS',
	UPDATE: 'UPDATE',
	UPDATE_AND_PROCCESS: 'UPDATE_AND_PROCCESS',
	SAVE_AND_PROCESS: 'SAVE_AND_PROCESS'
}
Object.freeze(TemplateAction);

var templateAction;
var processButtonClicked = false;
var backToOptionClicked = false;
var simulationClicked = false;

var DINT_GLOBAL_MAPPING_VARS = DINT_GLOBAL_MAPPING_VARS || {};

var templateSavedOrUpdated_dint = false;

//for buttons
function getSaveTemplateButton() {
	return document.getElementById('SAVE_TEMPLATE_BUTTON_ID');
}
function getUpdateTemplateButton() {
	return document.getElementById('UPDATE_TEMPLATE_BUTTON_ID');
}

function clickHiddenBackToOptionButton() {
	document.getElementById('HIDDEN_BACK_TO_OPTION_BUTTON_ID').click();
}
function clickHiddenSimulationButton() {
	document.getElementById('DINT_HIDDEN_SIMULATION_BUTTON_IN_GRAPHICAL_MAPPING').click();
}
function clickHiddenProcessButton() {
	document.getElementById('DINT_HIDDEN_PROCESS_BUTTON_ID_IN_GRAPHICAL_MAPPING').click();
}

function show(button) {
	if (button) {
		button.style.display = 'block';
	}
}

function hidden(button) {
	if (button) {
		button.style.display = 'none';
	}
}

function callFirstJS_dint() {
	resetGraphicalMappingChangeStatusParam();
	beforeUnloadEvent();
	checkUpdateTemplateButtonVisibility();
}

function beforeUnloadEvent() {
	window.addEventListener('beforeunload', function (e) {
		if (processButtonClicked || backToOptionClicked || simulationClicked) {
			return;
		}
		var changeMapping = isChangeMappingData();
		if ((DINT_GLOBAL_MAPPING_VARS.newTemplate && DINT_GLOBAL_MAPPING_VARS.requiredSaveTemplate)
			|| (!DINT_GLOBAL_MAPPING_VARS.newTemplate && DINT_GLOBAL_MAPPING_VARS.requiredUpdateTemplate)
			|| changeMapping) {
			//show confirm message when click Cancel from mapping screen
			(e || window.event).returnValue = false;
			return '';
		}
	});
}

function showTemplateResultMessage(data) {
	showInformationPopup(data.message);
}

function checkBeforeProcess() {
	if (!validateSourceTargetMapping()) {
		return;
	}
	processButtonClicked = true;
	if (DINT_GLOBAL_MAPPING_VARS.newTemplate) {
		if (DINT_GLOBAL_MAPPING_VARS.requiredSaveTemplate || isChangeMappingData()) {
			showSaveTemplateConfirm();
			return;
		}
		processWithCurrentMapping();
		return;
	}

	var isStatusChanged = (DINT_GLOBAL_MAPPING_VARS.requiredUpdateTemplate || isChangeMappingData());
	if (DINT_GLOBAL_MAPPING_VARS.hasModifyTemplatePermission && isStatusChanged) {
		showUpdateTemplateConfirm();
		return;
	}
	processWithCurrentMapping();
};
function showSaveTemplateConfirm() {
	dint_WrapYesAction = function () {
		ebx_dialogBox_hide();
		if (!saveTemplateAndProcess) { return; }
		saveTemplateAndProcess();
	};
	dint_WrapNoAction = function () {
		ebx_dialogBox_hide();
		if (!confirmNonUpdateTemplate) { return; }
		confirmNonSaveTemplate();
	};
	showConfirmDialog(DINT_GLOBAL_MAPPING_VARS.saveConfirmMessage, DINT_GLOBAL_MAPPING_VARS.closeButtonTooltip,
		DINT_GLOBAL_MAPPING_VARS.yesButtonLabel, DINT_GLOBAL_MAPPING_VARS.noButtonLabel, 'dint_WrapYesAction', 'dint_WrapNoAction');
};

function saveTemplateAndProcess() {
	templateAction = TemplateAction.SAVE_AND_PROCESS;
	dint_OpenPopupSaveUserTemplate(TemplateAction.SAVE);
}

function confirmNonSaveTemplate() {
	processWithCurrentMapping();
}

function showUpdateTemplateConfirm() {
	dint_WrapYesAction = function () {
		ebx_dialogBox_hide();
		if (!updateTemplateAndProcess) { return; }
		updateTemplateAndProcess();
	};
	dint_WrapNoAction = function () {
		ebx_dialogBox_hide();
		if (!confirmNonUpdateTemplate) { return; }
		confirmNonUpdateTemplate();
	};
	showConfirmDialog(DINT_GLOBAL_MAPPING_VARS.updateConfirmMessage, DINT_GLOBAL_MAPPING_VARS.closeButtonTooltip,
		DINT_GLOBAL_MAPPING_VARS.yesButtonLabel, DINT_GLOBAL_MAPPING_VARS.noButtonLabel, 'dint_WrapYesAction', 'dint_WrapNoAction');
};

function updateTemplateAndProcess() {
	templateAction = TemplateAction.UPDATE_AND_PROCCESS;
	dint_OpenPopupUpdateUserTemplate();
};

function confirmNonUpdateTemplate() {
	processWithCurrentMapping();
};

function startPersistTemplate() {
	if (templateAction === TemplateAction.SAVE || templateAction === TemplateAction.SAVEAS) {
		EBX_Utils.closeInternalPopup();
		handle_success_response_of_save_template_dint = handleSuccessSaveTemplate;
		handle_error_response_of_save_template_dint = showTemplateResultMessage;
		saveUserTemplate();
		return;
	}

	if (templateAction === TemplateAction.UPDATE) {
		EBX_Utils.closeInternalPopup();
		handle_success_response_of_update_template_dint = handleSuccessUpdateTemplate;
		handle_error_response_of_update_template_dint = showTemplateResultMessage;
		updateUserTemplate(DINT_GLOBAL_MAPPING_VARS.userTemplateUuid);
		return;
	}

	if (templateAction === TemplateAction.UPDATE_AND_PROCCESS) {
		EBX_Utils.closeInternalPopup();
		handle_success_response_of_update_template_dint = processAfterSaveOrUpdateTemplate;
		handle_error_response_of_update_template_dint = showTemplateResultMessage;
		updateUserTemplate(DINT_GLOBAL_MAPPING_VARS.userTemplateUuid);
		return;
	}

	if (templateAction === TemplateAction.SAVE_AND_PROCESS) {
		EBX_Utils.closeInternalPopup();
		handle_success_response_of_save_template_dint = processAfterSaveOrUpdateTemplate;
		handle_error_response_of_save_template_dint = showTemplateResultMessage;
		saveUserTemplate();
		return;
	}
};

function processAfterSaveOrUpdateTemplate() {
	templateSavedOrUpdated_dint = true;
	processWithCurrentMapping();
}

function handleSuccessSaveTemplate(data) {
	resetStatus();
	templateSavedOrUpdated_dint = true;
	DINT_GLOBAL_MAPPING_VARS.newTemplate = false;
	DINT_GLOBAL_MAPPING_VARS.hasModifyTemplatePermission = data.hasModifyTemplatePermission;
	DINT_GLOBAL_MAPPING_VARS.userTemplateUuid = data.savedTemplateUuid;
	checkUpdateTemplateButtonVisibility();

	document.getElementById(SAVE_AS_TEMPLATE_BUTTON_ID).classList.remove('dint-hidden');

	if (templateAction === TemplateAction.SAVE) {
		hidden(getSaveTemplateButton());
	}
	showTemplateResultMessage(data);
}

function populateMappingChangedStatusAndTemplateStatus() {
	var hiddenInput = document.getElementById('HIDDEN_MAPPING_CHANGED_STATUS_ID_dint');
	hiddenInput.value = isMappingStatusChanged();
	console.log('>>> populateMappingChangedStatus = ' + hiddenInput.value);
	var templateUpdated = document.getElementById('HIDDEN_TEMPLATE_SAVED_OR_UPDATED_ID_dint');
	templateUpdated.value = templateSavedOrUpdated_dint;
	console.log('>>> templateSavedOrUpdated_dint = ' + templateSavedOrUpdated_dint);
}

function populateMappingDTO() {
	var hiddenInput = document.getElementById('graphical-mapping-data-id');
	hiddenInput.value = getGraphicalMappingData();
}

function resetStatus() {
	DINT_GLOBAL_MAPPING_VARS.templateOptionChanged = false;
	DINT_GLOBAL_MAPPING_VARS.requiredSaveTemplate = false;
	DINT_GLOBAL_MAPPING_VARS.requiredUpdateTemplate = false;
	resetGraphicalMappingChangeStatusParam();
	resetChangeMappingDataStatusParam();
}

function handleSuccessUpdateTemplate(data) {
	resetStatus();
	templateSavedOrUpdated_dint = true;
	showTemplateResultMessage(data);
}

function handleSaveTemplateRequest(action) {
	const mappingData = getMappingData();
	if (isMappingDataInvalid(mappingData)) {
		ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.userTemplateSaveErrorMessage);
		return;
	}
	if (mappingData.targetTable.fields.length === 0) {
		ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.emptyFieldSaveTemplateMessage);
		return;
	}
	var angularDiagramRef = window._mapping_frame_id.frames[0].window.angularDiagramComponentReference;
	if (isTargetFieldLabelEmpty()) {
		angularDiagramRef.emptyFieldLabelCallFromOutsideAngular();
		return;
	}

	templateAction = action;
	dint_OpenPopupSaveUserTemplate(templateAction);
}

function checkUpdateTemplateButtonVisibility() {
	var button = getUpdateTemplateButton();
	DINT_GLOBAL_MAPPING_VARS.hasModifyTemplatePermission ? show(button) : hidden(button);
}

function doCancel() {
	document.getElementById(HIDDEN_CANCEL_BUTTON_ID).click();
}

function doNoCancel() {
	return;
}

function onBackToOptionClick_dint() {
	if (!validateEmptySourceTargetMapping()) {
		return;
	}
	backToOptionClicked = true;
	populateMappingDTO();
	populateMappingChangedStatusAndTemplateStatus();
	clickHiddenBackToOptionButton();
}

function processWithCurrentMapping() {
	populateMappingDTO();
	populateMappingChangedStatusAndTemplateStatus();
	clickHiddenProcessButton();
}

function handleUpdateTemplateRequest() {
	var mappingData = getMappingData();
	if (isMappingDataInvalid(mappingData)) {
		ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.updateTemplateFailedMessage);
		return;
	}
	if (mappingData.targetTable.fields.length === 0) {
		ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.emptyFieldUpdateTemplateMessage);
		return;
	}
	var angularDiagramRef = window._mapping_frame_id.frames[0].window.angularDiagramComponentReference;
	if (isTargetFieldLabelEmpty()) {
		angularDiagramRef.emptyFieldLabelCallFromOutsideAngular();
		return;
	}
	templateAction = TemplateAction.UPDATE;
	dint_OpenPopupUpdateUserTemplate();
}

function isMappingDataInvalid(mappingData) {
	return !mappingData.sourceTable || !mappingData.targetTable;
}

function isTargetFieldLabelEmpty() {
	var mappingData = getMappingData();
	if (mappingData.targetTable) {
		for (let index in mappingData.targetTable.fields) {
			const field = mappingData.targetTable.fields[index];
			if (!field.label) {
				return true;
			}
		}
	}
	return false;
}

function getMappingData() {
	return JSON.parse(getGraphicalMappingData());
}

function getTransformParamErrorMessages() {
	const messages = [];
	var mappingData = getMappingData();
	for (let index in mappingData.transformationFunctions) {
		const transform = mappingData.transformationFunctions[index];
		for (let j in transform.params) {
			const param = transform.params[j];
			if (param.errorMessage) { messages.push(param.errorMessage); }
		}
	}
	return messages;
}

function handle_response_of_save_template_dint(resObj) {
	var data = JSON.parse(resObj.response);

	if (data.severity === 'ERROR') {
		handle_error_response_of_save_template_dint(data);
		return;
	}
	handle_success_response_of_save_template_dint(data);
}

function handle_response_of_update_template_dint(resObj) {
	var data = JSON.parse(resObj.response);

	if (data.severity === 'ERROR') {
		handle_error_response_of_update_template_dint(data);
		return;
	}
	handle_success_response_of_update_template_dint(data);
}

function dint_OpenPopupSaveUserTemplate(action) {
	EBX_Utils.openInternalPopup(DINT_GLOBAL_MAPPING_VARS.templateServiceUri + '&action=' + action, 1030, 736,
		{ isCloseButtonDisplayed: true, isResizable: false, isCloseButtonOnPopupEdge: false });
}

function saveUserTemplate() {
	var url = DINT_GLOBAL_MAPPING_VARS.dintSaveurl;
	var params = getGraphicalMappingData();
	sendPOSTRequest(url, params, handle_response_of_save_template_dint);
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

function dint_OpenPopupUpdateUserTemplate() {
	EBX_Utils.openInternalPopup(DINT_GLOBAL_MAPPING_VARS.templateServiceUri + '&action=UPDATE', 1030, 736,
		{ isCloseButtonDisplayed: true, isResizable: false, isCloseButtonOnPopupEdge: false });
}

function updateUserTemplate(templateUuid) {
	var url = DINT_GLOBAL_MAPPING_VARS.dintUpdateurl;
	url = url + '&userTemplateUuid=' + templateUuid;
	var params = getGraphicalMappingData();
	sendPOSTRequest(url, params, handle_response_of_update_template_dint);
}

function onSimulationClick_dint() {
	if (!validateSourceTargetMapping()) {
		return;
	}
	simulationClicked = true;
	populateMappingDTO();
	populateMappingChangedStatusAndTemplateStatus();
	clickHiddenSimulationButton();
}

function validateSourceTargetMapping() {
	var angularDiagramRef = window._mapping_frame_id.frames[0].window.angularDiagramComponentReference;
	if (isTargetFieldLabelEmpty()) {
		angularDiagramRef.emptyFieldLabelCallFromOutsideAngular();
		return false;
	}
	var errorMessages = getTransformParamErrorMessages();
	if (errorMessages.length > 0) {
		angularDiagramRef.showErrorMessageFromOutsideAngular(errorMessages);
		return false;
	}

	var mappingData = getMappingData();
	if (!mappingData.sourceTable && !mappingData.targetTable) {
		angularDiagramRef.showErrorMessageFromOutsideAngular([DINT_GLOBAL_MAPPING_VARS.emptyMappingTabMessage]);
		return false;
	}
	if (!mappingData.sourceTable || !mappingData.targetTable) {
		angularDiagramRef.showErrorMessageFromOutsideAngular([DINT_GLOBAL_MAPPING_VARS.sourceOrTargetDeletedMessage]);
		return false;
	}
	if ('ERROR' === mappingData.state) {
		angularDiagramRef.showErrorMessageFromOutsideAngular([DINT_GLOBAL_MAPPING_VARS.errorMappingTabsMessage]);
		return false;
	}
	return true;
}

function validateEmptySourceTargetMapping() {
	var angularDiagramRef = window._mapping_frame_id.frames[0].window.angularDiagramComponentReference;
	var mappingData = getMappingData();
	if (!mappingData.sourceTable || !mappingData.targetTable) {
		angularDiagramRef.showErrorMessageFromOutsideAngular([DINT_GLOBAL_MAPPING_VARS.sourceOrTargetDeletedMessage]);
		return false;
	}
	return true;
}
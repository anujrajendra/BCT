var DINT_GLOBAL_TEMPLATE_VARS = DINT_GLOBAL_TEMPLATE_VARS || {};
function clickLoadUserTemplate() {
	resetChangeMappingDataStatusParam();
	document.getElementById('DINT_HIDDEN_INPUT_LOAD_USER_TEMPLATE_ID').value = 'LOAD_USER_TEMPLATE';
	var dint_submitCSVOptionBtn = document.getElementById('DINT_LOAD_USER_TEMPLATE_BUTTON_ID');
	dint_submitCSVOptionBtn.disabled = false;
	dint_submitCSVOptionBtn.click();
}

function resetChangeMappingDataStatusParam() {
    const IS_CHANGE_MAPPING_DATA_PARAM = 'IS_CHANGE_MAPPING_DATA_PARAM'; //status of changing node or link data
	window.sessionStorage.setItem(IS_CHANGE_MAPPING_DATA_PARAM, 'false');
}

function showConfirmChangeOptionDialog() {
	doYesAction = function () {
		document.getElementById(DINT_GLOBAL_TEMPLATE_VARS.yesActionButtonId).click();
	};
	doNoAction = function () {
		document.getElementById(DINT_GLOBAL_TEMPLATE_VARS.noActionButtonId).click();
	};
	showConfirmDialog(DINT_GLOBAL_TEMPLATE_VARS.confirmQuestion, DINT_GLOBAL_TEMPLATE_VARS.closeButtonTooltip,
		DINT_GLOBAL_TEMPLATE_VARS.yesButtonLabel,
		DINT_GLOBAL_TEMPLATE_VARS.noButtonLabel, 'doYesAction', 'doNoAction');
}

function showConfirmChangeOptionDialogWithoutNoButton() {
	doYesAction = function () {
		document.getElementById(DINT_GLOBAL_TEMPLATE_VARS.yesActionButtonId).click();
	};
	showConfirmDialogWith1Button(DINT_GLOBAL_TEMPLATE_VARS.confirmQuestion, DINT_GLOBAL_TEMPLATE_VARS.closeButtonTooltip,
		DINT_GLOBAL_TEMPLATE_VARS.yesButtonLabel,
		'doYesAction');
}

function changeTemplatePermissionhHanlder(value, widgetName) {
	let widgetNames = widgetName.split(',');
	let useTemplatewidgetName = widgetNames[0];
	let modifyTemplatewidgetName = widgetNames[1];
	let deleteTemplatewidgetName = widgetNames[2];
	let isModifyChecked = document.getElementsByName(modifyTemplatewidgetName)[0].checked;
	let isDeleteChecked = document.getElementsByName(deleteTemplatewidgetName)[0].checked;
	if (isModifyChecked === true || isDeleteChecked === true) {
		document.getElementsByName(useTemplatewidgetName)[0].checked = true;
		document.getElementsByName(useTemplatewidgetName)[1].disabled = true;
	} else {
		document.getElementsByName(useTemplatewidgetName)[1].disabled = false;
	}
}
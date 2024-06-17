const TemplateAction = {
    SAVE: 'SAVE',
    SAVEAS: 'SAVEAS',
    UPDATE: 'UPDATE',
    UPDATE_AND_PROCCESS: 'UPDATE_AND_PROCCESS',
    SAVE_AND_PROCESS: 'SAVE_AND_PROCESS'
};
Object.freeze(TemplateAction);

const mappingFrameID = '_mapping_frame_id';
const SAVE_TEMPLATE_BUTTON_ID = 'SAVE_TEMPLATE_BUTTON_ID';
const UPDATE_TEMPLATE_BUTTON_ID = 'UPDATE_TEMPLATE_BUTTON_ID';
const SAVE_AS_TEMPLATE_BUTTON_ID = 'SAVE_AS_TEMPLATE_BUTTON_ID';

var DINT_GLOBAL_MAPPING_VARS = DINT_GLOBAL_MAPPING_VARS || {};
var templateAction = '';
var processAfter = '';

var templateSavedOrUpdated_dint = false;

//>>>>>>>>>>>>>
function startPersistTemplate() {
    if (templateAction === TemplateAction.SAVE || templateAction === TemplateAction.SAVEAS) {
        EBX_Utils.closeInternalPopup();
        saveUserTemplate(handleSuccessSaveTemplate, showTemplateResultMessage);
        return;
    }

    if (templateAction === TemplateAction.UPDATE) {
        EBX_Utils.closeInternalPopup();
        updateUserTemplate(DINT_GLOBAL_MAPPING_VARS.templateUuid,
            handleSuccessUpdateTemplate,
            showTemplateResultMessage);
        return;
    }

    if (templateAction === TemplateAction.UPDATE_AND_PROCCESS) {
        EBX_Utils.closeInternalPopup();
        updateUserTemplate(DINT_GLOBAL_MAPPING_VARS.templateUuid,
            processAfter, showTemplateResultMessage);
        return;
    }

    if (templateAction === TemplateAction.SAVE_AND_PROCESS) {
		EBX_Utils.closeInternalPopup();
		saveUserTemplate(processAfter, showTemplateResultMessage);
		return;
	}
};

function processAfterSaveOrUpdateTemplate() {
	templateSavedOrUpdated_dint = true;
	proccessCurrentMapping();
}

function showSaveTemplateConfirm() {
	dint_WrapYesAction = function () {
		ebx_dialogBox_hide();
		saveTemplateAndProcess();
	};
	dint_WrapNoAction = function () {
		ebx_dialogBox_hide();
		proccessCurrentMapping();
	};
	showConfirmDialog(DINT_GLOBAL_MAPPING_VARS.saveConfirmMessage, DINT_GLOBAL_MAPPING_VARS.closeButtonTooltip,
		DINT_GLOBAL_MAPPING_VARS.yesButtonLabel, DINT_GLOBAL_MAPPING_VARS.noButtonLabel, 'dint_WrapYesAction', 'dint_WrapNoAction');
};

function saveTemplateAndProcess() {
    processAfter = processAfterSaveOrUpdateTemplate;
	templateAction = TemplateAction.SAVE_AND_PROCESS;
	dint_OpenTemplatePopup(TemplateAction.SAVE);
}

function showUpdateTemplateConfirm() {
    dint_WrapYesAction = function () {
        ebx_dialogBox_hide();
        updateTemplateAndProcess();
    };
    dint_WrapNoAction = function () {
        ebx_dialogBox_hide();
        proccessCurrentMapping();
    };
    showConfirmDialog(DINT_GLOBAL_MAPPING_VARS.updateConfirmMessage, DINT_GLOBAL_MAPPING_VARS.closeButtonTooltip,
        DINT_GLOBAL_MAPPING_VARS.yesButtonLabel, DINT_GLOBAL_MAPPING_VARS.noButtonLabel, 'dint_WrapYesAction', 'dint_WrapNoAction');
};

function updateTemplateAndProcess() {
    processAfter = processAfterSaveOrUpdateTemplate;
    templateAction = TemplateAction.UPDATE_AND_PROCCESS;
    dint_OpenTemplatePopup(TemplateAction.UPDATE);
};

function handleSuccessUpdateTemplate(data) {
    resetStatus();
    templateSavedOrUpdated_dint = true;
    showTemplateResultMessage(data);
}

function updateUserTemplate(templateUuid, handleSuccess, handleFail) {
    var url = DINT_GLOBAL_MAPPING_VARS.dintUpdateurl;
    url = url + '&userTemplateUuid=' + templateUuid;
    var params = getGraphicalMappingData();
    sendPOSTRequest(url, params, handleSuccess, handleFail);
}

function saveUserTemplate(handleSuccess, handleFail) {
    var url = DINT_GLOBAL_MAPPING_VARS.dintSaveurl;
    var params = getGraphicalMappingData();
    sendPOSTRequest(url, params, handleSuccess, handleFail);
}

function sendPOSTRequest(url, params, handleSuccess, handleFail) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.response);
            if (data.severity === 'ERROR') {
                handleFail(data);
                return;
            }
            handleSuccess(data);
        }
    };
    xhttp.open('POST', url, true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.send(params);
}

function handleSuccessSaveTemplate(data) {
    resetStatus();
    DINT_GLOBAL_MAPPING_VARS.newTemplate = false;
    templateSavedOrUpdated_dint = true;
    DINT_GLOBAL_MAPPING_VARS.hasModifyTemplatePermission = data.hasModifyTemplatePermission;
    DINT_GLOBAL_MAPPING_VARS.templateUuid = data.savedTemplateUuid;
    checkUpdateTemplateButtonVisibility();
    document.getElementById(SAVE_AS_TEMPLATE_BUTTON_ID).classList.remove('dint-hidden');

    if (templateAction === TemplateAction.SAVE) {
        document.getElementById(SAVE_TEMPLATE_BUTTON_ID).style.display = 'none';
    }

    showTemplateResultMessage(data);
}

function checkUpdateTemplateButtonVisibility() {
    var e = document.getElementById(UPDATE_TEMPLATE_BUTTON_ID);
    if (DINT_GLOBAL_MAPPING_VARS.hasModifyTemplatePermission) {
        e.style.display = 'block';
        return;
    } else {
        e.style.display = 'none';
    }
}

function showTemplateResultMessage(data) {
    showInformationPopup(data.message);
    // switch (data.severity) {
    //     case 'INFO':
    //         ebx_messageBox_addInfo(data.message);
    //         EBX_UserMessageManager.displayMessageBox();
    //         break;
    //     case 'WARNING':
    //         ebx_messageBox_addWarning(data.message); break;
    //     case 'ERROR':
    //         ebx_messageBox_addError(data.message); break;
    // };
}

function resetStatus() {
    DINT_GLOBAL_MAPPING_VARS.templateOptionChanged = false;
    DINT_GLOBAL_MAPPING_VARS.requiredSaveTemplate = false;
	DINT_GLOBAL_MAPPING_VARS.requiredUpdateTemplate = false;
    resetGraphicalMappingChangeStatusParam();
    resetChangeMappingDataStatusParam();
}

function handleSaveTemplateRequest(action) {
    const mappingData = JSON.parse(getGraphicalMappingData());
    const tableMappings = mappingData.tableMappings;

    let dataError = false;
    if (isMappingDataInvalid(tableMappings)) {
        ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.userTemplateSaveErrorMessage);
        dataError = true;
    }
    if (isContainEmptyField(tableMappings)) {
        ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.emptyFieldSaveTemplateMessage);
        dataError = true;
    }
    if (isContainTargetFieldLabelEmpty(tableMappings)) {
        showError([DINT_GLOBAL_MAPPING_VARS.emptyFieldLabelSaveTemplateMessage]);
        dataError = true;
    }

    if (dataError) { return; }

    templateAction = action;
    dint_OpenTemplatePopup(action);
}

function isContainEmptyField(mappingData) {
    for (var i in mappingData) {
        var targetTables = mappingData[i].targetTables;
        for (var j in targetTables) {
            var targetTable = targetTables[j];
            if (targetTable.fields.length === 0) {
                return true;
            }
        }
    }
    return false;
}

function isContainTargetFieldLabelEmpty(mappingData) {
    for (var i in mappingData) {
        var targetTables = mappingData[i].targetTables;
        for (var j in targetTables) {
            var targetTable = targetTables[j]
            if (isContainFieldLabelEmpty(targetTable)) {
                return true;
            }
        }
    }
    return false;
}

function isContainFieldLabelEmpty(table) {
    var fields = table.fields;
    for (var i in fields) {
        if (!fields[i].label) {
            return true;
        }
    }
    return false;
}

function isMappingDataInvalid(mappingData) {
    for (var i in mappingData) {
        var data = mappingData[i];
        if (!data.sourceTables.length || !data.targetTables.length) {
            return true;
        }
    }
    return false;
}

function handleUpdateTemplateRequest() {
    const mappingData = JSON.parse(getGraphicalMappingData());
    const tableMappings = mappingData.tableMappings;

    let dataError = false;
    if (isMappingDataInvalid(tableMappings)) {
        ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.updateTemplateFailedMessage);
        dataError = true;
    }
    if (isContainEmptyField(tableMappings)) {
        ebx_messageBox_addError(DINT_GLOBAL_MAPPING_VARS.emptyFieldUpdateTemplateMessage);
        dataError = true;
    }
    if (isContainTargetFieldLabelEmpty(tableMappings)) {
        showError([DINT_GLOBAL_MAPPING_VARS.emptyFieldLabelSaveTemplateMessage]);
        dataError = true;
    }

    if (dataError) { return; }

    templateAction = TemplateAction.UPDATE;
    dint_OpenTemplatePopup(templateAction);
}

function dint_OpenTemplatePopup(action) {
    EBX_Utils.openInternalPopup(DINT_GLOBAL_MAPPING_VARS.templateServiceUri
        + '&action=' + action,
        1030, 736, { isCloseButtonDisplayed: true, isResizable: false, isCloseButtonOnPopupEdge: false }
    );
}

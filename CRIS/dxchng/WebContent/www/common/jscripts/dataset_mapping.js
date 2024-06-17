var backToOptionClicked = false;
var processButtonClicked = false;
var simulationClicked = false;

var DINT_GLOBAL_MAPPING_VARS = DINT_GLOBAL_MAPPING_VARS || {};

// for button setup in backend
function clickHiddenSimulationButton() {
    document.getElementById('DINT_HIDDEN_SIMULATION_BUTTON_IN_GRAPHICAL_MAPPING').click();
}

function clickHiddenBackToOption() {
    document.getElementById('HIDDEN_BACK_TO_OPTION_BUTTON_ID').click();
}
function clickHiddenProcess() {
    document.getElementById('DINT_HIDDEN_PROCESS_BUTTON_ID_IN_GRAPHICAL_MAPPING').click();
}

function beforeUnloadEvent() {
    window.addEventListener('beforeunload', function (e) {
        if (processButtonClicked || backToOptionClicked || simulationClicked) {
            return;
        }

        var changeMapping = isChangeMappingData();
        const defaultOptionChanged = DINT_GLOBAL_MAPPING_VARS.newTemplate && DINT_GLOBAL_MAPPING_VARS.defaultOptionChanged;
        const templatedOptionChanged = !DINT_GLOBAL_MAPPING_VARS.newTemplate && DINT_GLOBAL_MAPPING_VARS.templateOptionChanged;
        if (defaultOptionChanged || templatedOptionChanged || changeMapping) {
            (e || window.event).returnValue = false; //show confirm message when click Cancel from mapping screen
            return '';
        }
    });
}

function onExecuteClick_dint() {
    if (checkErrorTabMapping()) {
        return;
    }
    processButtonClicked = true;
    
    if (DINT_GLOBAL_MAPPING_VARS.newTemplate) {
		if (DINT_GLOBAL_MAPPING_VARS.requiredSaveTemplate || isChangeMappingData()) {
			showSaveTemplateConfirm();
			return;
		}
		proccessCurrentMapping();
		return;
	}

    const statusChanged = (DINT_GLOBAL_MAPPING_VARS.requiredUpdateTemplate || isChangeMappingData());
    if (DINT_GLOBAL_MAPPING_VARS.hasModifyTemplatePermission && statusChanged) {
        showUpdateTemplateConfirm();
        return;
    }
    proccessCurrentMapping();
}

function checkErrorTabMapping() {
    const mappingData = JSON.parse(getGraphicalMappingData());
    const tableMappings = mappingData.tableMappings;

    const errorTab = [];
    let emptyMapping = false;
    let sourceOrTargetDeleted = false;

    for (var index in tableMappings) {
        var data = tableMappings[index];
        if (!data.sourceTables.length && !data.targetTables.length) {
            emptyMapping = true;
            continue;

        } else if (!data.sourceTables.length || !data.targetTables.length) {
            sourceOrTargetDeleted = true;
            continue;
        }
        if ('ERROR' === data.state) {
            errorTab.push(data);
        }
    }
    if (emptyMapping) {
        showError([DINT_GLOBAL_MAPPING_VARS.emptyMappingTabMessage]);
    }
    if (sourceOrTargetDeleted) {
        showError([DINT_GLOBAL_MAPPING_VARS.sourceOrTargetDeletedMessage]);
    }
    if (errorTab.length) {
        let mess = '';
        if (errorTab.length === 1) {
            mess = errorTab[0].sourceTables[0].label;
        } else {
            let i = 0;
            for (i = 0; i < errorTab.length - 1; i++) {
                mess = mess + errorTab[i].sourceTables[0].label + ', '
            }
            mess = mess.substring(0, mess.length - 2);
            mess = mess + ' ' + DINT_GLOBAL_MAPPING_VARS.andLabel + ' '
                + errorTab[i].sourceTables[0].label;
        }
        showError([DINT_GLOBAL_MAPPING_VARS.errorMappingTabsMessage + ' ' + mess]);
    }

    return emptyMapping || sourceOrTargetDeleted || errorTab.length > 0;
}

function showError(messages) {
    const angularDiagramRef = window._mapping_frame_id.frames[0].window.angularDatasetMappingComponentReference;
    angularDiagramRef.showErrorMessageFromOutsideAngular(messages);
}

function setMappingDataIntoField() {
    const hiddenInput = document.getElementById('graphical-mapping-data-id');
    hiddenInput.value = getGraphicalMappingData();
}

function populateMappingChangedStatusAndTemplateStatus() {
	var hiddenInput = document.getElementById('HIDDEN_MAPPING_CHANGED_STATUS_ID_dint');
	hiddenInput.value = isMappingStatusChanged();
	console.log('>>> populateMappingChangedStatus = ' + hiddenInput.value);
	var templateUpdated = document.getElementById('HIDDEN_TEMPLATE_SAVED_OR_UPDATED_ID_dint');
	templateUpdated.value = templateSavedOrUpdated_dint;
	console.log('>>> templateSavedOrUpdated_dint = ' + templateSavedOrUpdated_dint);
}

function proccessCurrentMapping() {
    setMappingDataIntoField();
    populateMappingChangedStatusAndTemplateStatus();
    clickHiddenProcess();
}

function onBackToOptionClick_dint() {
    backToOptionClicked = true;
    setMappingDataIntoField();
    populateMappingChangedStatusAndTemplateStatus();
    clickHiddenBackToOption();
}

function onSimulationClick_dint() {
    simulationClicked = true;
    setMappingDataIntoField();
    populateMappingChangedStatusAndTemplateStatus();
    clickHiddenSimulationButton();
}

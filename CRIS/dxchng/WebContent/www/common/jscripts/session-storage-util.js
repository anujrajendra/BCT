const IS_CHANGE_MAPPING_DATA_PARAM = 'IS_CHANGE_MAPPING_DATA_PARAM'; //status of changing node or link data
const GRAPHICAL_MAPPING_CHANGE_STATUS_PARAM = 'GRAPHICAL_MAPPING_CHANGE_STATUS_PARAM'; //status of changing node or link data + position

const sessionStorageUtil = {
	resetChangeMappingDataStatusParam: resetChangeMappingDataStatusParam,
	resetGraphicalMappingChangeStatusParam: resetGraphicalMappingChangeStatusParam
}

function isChangeMappingData() {
	return window.sessionStorage.getItem(IS_CHANGE_MAPPING_DATA_PARAM) === 'true';
}

function resetChangeMappingDataStatusParam() {
	window.sessionStorage.setItem(IS_CHANGE_MAPPING_DATA_PARAM, 'false');
}

function resetGraphicalMappingChangeStatusParam() {
	window.sessionStorage.setItem(GRAPHICAL_MAPPING_CHANGE_STATUS_PARAM, 'false');
}

function isMappingStatusChanged() {
	return window.sessionStorage.getItem(GRAPHICAL_MAPPING_CHANGE_STATUS_PARAM) === 'true';
}

function getGraphicalMappingData() {
	return window.sessionStorage.getItem('GraphicalMappingData');
}
getSQLTableOrView.prototype = new EBX_AJAXResponseHandler();
var dintIconLoading = 'dintIconLoading';

function hiddenIconLoading() {
	document.getElementById(dintIconLoading).style.display = 'none';
}

function displayIconLoading() {
	document.getElementById(dintIconLoading).style.display = 'block';
}

function dint_callajaxcomponent_sql_datasource(code) {
	const sqlTableSelects = document.getElementById(sqlTable);
	sqlTableSelects.disabled = true;
	displayIconLoading();
	removeOptions();
	var ajaxHandler = new getSQLTableOrView();
	ajaxHandler.sendRequest(ajaxURL + requestParameter+ '&code='+ebx_encodeQueryParam(code));
}

function getSQLTableOrView() {
	const sqlTableSelects = document.getElementById(sqlTable);

	this.handleAjaxResponseSuccess = function(responseContent) {
		sqlTableSelects.disabled = false;
		hiddenIconLoading();
	};
}
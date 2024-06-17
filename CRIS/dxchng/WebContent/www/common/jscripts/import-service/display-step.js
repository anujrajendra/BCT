document.addEventListener("DOMContentLoaded", function(event) {
	var importFile = document.getElementById("dintImportFile");
	if(importFile) {
		importFile.addEventListener("change", dint_reloadOption);
	}
});

function dint_reloadOption() {
    let button = document.getElementById("HIDDEN_MAPPING_RESETTING_RELOAD_BUTTON_ID_dint");
    button.disabled = false;
    button.click();
};
document.addEventListener("DOMContentLoaded", function(event) {
	var importFile = document.getElementById("dintImportFile");
	if(importFile) {
		importFile.addEventListener("change", dint_reloadOption);
	}
});

function dint_reloadOption() {
    let button = document.getElementById("dint_reload_option_button_id");
    button.disabled = false;
    button.click();
};
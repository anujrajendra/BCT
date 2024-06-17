function domReady(fn) {
	document.addEventListener("DOMContentLoaded", fn);
	if (document.readyState === "interactive" || document.readyState === "complete") {
		fn();
	}
}

const simulation = {
	init() {
		this.loadPage();
	},
	loadPage() {
		document.addEventListener("DOMContentLoaded", function() {
			var tableContentitem = document.querySelector(".dint-simulation-step__tableContent");
			tableContentitem.style.display = 'block';
		});
	}
}

simulation.init();

function clickHiddenProcessButton() {
    document.getElementById('hiddenProcessButton_dint').click();
}

function process() {
    clickHiddenProcessButton();
}

function showTemplateResult(data) {
    showInformationPopup(data.message);
}

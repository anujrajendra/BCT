var optionIndex = "3";
var DINT_SIMULATION_CUSTOMIZE = "dint-simulation-customize";
var onDisplayCustomize = (value) => {
	if (value == optionIndex) {
		document.getElementById(DINT_SIMULATION_CUSTOMIZE).style.display = 'flex';
	} else {
		document.getElementById(DINT_SIMULATION_CUSTOMIZE).style.display = 'none';
	}
}

function domReady(fn) {
	document.addEventListener("DOMContentLoaded", fn);
	if (document.readyState === "interactive" || document.readyState === "complete") {
		fn();
	}
}

function submitSimulation() {
	document.getElementById("SIMULATION-EXECUTE-BUTTON").click();
}

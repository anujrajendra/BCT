function onEnableDropdownList(value, dropdownlist) {
	var dropdownlist = document.querySelector("#"+dropdownlist+ " select");
	if(value[0] === "true" && dropdownlist) {
		dropdownlist.style.pointerEvents = 'unset';
	} else {
		dropdownlist.style.pointerEvents = 'none';
		dropdownlist.selectedIndex = "0";
	}
}

function getValueCheckbox(checkboxID) {
	return checkboxID.value;
}

function domReady(fn) {
  // If we're early to the party
  document.addEventListener("DOMContentLoaded", fn);
  // If late; I mean on time.
  if (document.readyState === "interactive" || document.readyState === "complete" ) {
    fn();
  }
}
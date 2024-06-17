function openTab(evt, menu) {
	let i, tabcontent, tablinks;
	tabcontent = document.getElementById("tab-content-detail").getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementById("tab-links-content").getElementsByTagName("li");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace("selected", "");
	}
	document.getElementById(menu).style.display = "block";
	document.getElementById('TabLink_' + menu).className = "selected";


	// Selected move scroll
	let indexSelected = getIndexSelected();
	let currentWidthTab = getCurrentWidthTab(indexSelected-1) + 400;
	let widthTab = calculatorWidthTab();
	
	if (currentWidthTab > widthTab) {
		translate = widthTab - currentWidthTab;
		document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
	} else {
		translate = 0;
		document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
	}
	
}


function openTabOnTableSummary(evt, menu) {
	var i, tabcontent, tablinks;
	tabcontent = document.getElementById("tab-content-detail").getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementById("tab-links-content").getElementsByTagName("li");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace("selected", "");
	}
	document.getElementById(menu).style.display = "block";
	document.getElementById('TabLink_' + menu).className = "selected";

	let indexSelected = getIndexSelected();
	let currentWidthTab = getCurrentWidthTab(indexSelected) + 200;
	let widthTab = calculatorWidthTab();

	if (currentWidthTab > widthTab) {
		translate = widthTab - currentWidthTab;
		document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
	} else {
		translate = 0;
		document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
	}
}

function naviTab(n) {
	let navTabIndex = getIndexSelected() + n;
	showNavTabs(navTabIndex);
}

function getIndexSelected() {
	let navTabs = document.getElementById("tab-links-content").getElementsByTagName("li");
	var num = 0;
	for (var i = 0; i < navTabs.length; i++) {
		if (navTabs[i].className == "selected") {
			return num;
		}
		num++;
	}
}

function showNavTabs(n) {
	let i;
	let navTabs = document.getElementById("tab-links-content").getElementsByTagName("li");
	let tabcontent = document.getElementById("tab-content-detail").getElementsByClassName("tabcontent");
	let navTabIndex = n;

	for (i = 0; i < navTabs.length; i++) {
		navTabs[i].className = navTabs[i].className.replace("selected", "");
		tabcontent[i].style.display = "none";
	}

	if (n >= navTabs.length) { navTabIndex = 0;}
	if (n < 0) { navTabIndex = navTabs.length - 1; }

	navTabs[navTabIndex].className = "selected";
	tabcontent[navTabIndex].style.display = "block";
}

moveTabInScroll();
function moveTabInScroll() {
	let translate = 0;

	let buttonLeft = document.getElementById("buttonLeft");
	buttonLeft.addEventListener("click", function () {
		let navTabIndex = getIndexSelected();
		let widthTab = calculatorWidthTab();
		let currentWidthTab = getCurrentWidthTab(navTabIndex-1) + 300;

		if (currentWidthTab > widthTab) {
			translate = widthTab - currentWidthTab;
			document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
		} else {
			translate = 0;
			document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
		}

	});

	const buttonRight = document.getElementById("buttonRight");
	buttonRight.addEventListener("click", function () {
		let navTabIndex = getIndexSelected();
		let widthTab = calculatorWidthTab();
		let currentWidthTab = getCurrentWidthTab(navTabIndex) + 200;

		if (currentWidthTab > widthTab) {
			translate = widthTab - currentWidthTab;
			document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
		} else {
			translate = 0;
			document.getElementById("tab-links-content").style.transform = "translateX(" + translate + "px" + ")";
		}
	});
}

function getCurrentWidthTab(index) {
	let currentWidthTab = calculatorWidthTabById("#TabLink_Summary");
	let navTabs = document.getElementById("tab-links-content").getElementsByTagName("li");
	for (j = 0; j <= index; j++) {
		currentWidthTab = currentWidthTab + parseInt(window.getComputedStyle(navTabs[j]).width.replace('px', ''));
	}
	return currentWidthTab;
}

function calculatorWidthTab() {
	let tab = document.getElementById("DintTabResult").querySelector(".tab-links-items");
	let widthTab = tab.clientWidth;
	return widthTab;
}

function calculatorWidthTabById(nameTab) {
	let tab = document.querySelector(nameTab);
	let widthTab = tab.clientWidth;
	return widthTab;
}
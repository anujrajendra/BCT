function showConfirmDialog(title, closeButtonToolip, yesLabel, noLabel, yesFunction, noFunction) {
	var header = "<a class='container-close' href='#' onclick= ebx_dialogBox_hide() title='" + closeButtonToolip + "'>Close</a>"
		+ "<h2 class='ebx_dialog_header' >" + title + "</h2>";
	var footer = "<div class='ebx_DialogBoxFooterToolbar' >"
		+ "<button type= 'button' onclick= '" + yesFunction + "();' class = 'ebx_Button ebx_DefaultButton'>" + yesLabel + "</button>"
		+ "<button type= 'button' onclick= '" + noFunction + "();' class = 'ebx_Button'>" + noLabel + "</button>"
		+ "</div>";
	ebx_dialogBox_show({
		header: header,
		footer: footer,
		jsCommandClose: ebx_dialogBox_hide
	});
}

function showConfirmDialogWith1Button(title, closeButtonToolip, yesLabel, yesFunction) {
	var header = "<a class='container-close' href='#' onclick= ebx_dialogBox_hide() title='" + closeButtonToolip + "'>Close</a>"
		+ "<h2 class='ebx_dialog_header' >" + title + "</h2>";
	var footer = "<div class='ebx_DialogBoxFooterToolbar' >"
		+ "<button type= 'button' onclick= '" + yesFunction + "();' class = 'ebx_Button ebx_DefaultButton'>" + yesLabel + "</button>"
		+ "</div>";
	ebx_dialogBox_show({
		header: header,
		footer: footer,
		jsCommandClose: ebx_dialogBox_hide
	});
}

function showInformationPopup(content) {
	var header = "<h2 class='ebx_dialog_header' >" + content + "</h2>";
	var footer = "<div class='ebx_DialogBoxFooterToolbar' >"
		+ "<button type= 'button' onclick = ebx_dialogBox_hide() class = 'ebx_Button ebx_DefaultButton'>Close</button>"
		+ "</div>";
	ebx_dialogBox_show({
		header: header,
		footer: footer,
		jsCommandClose: ebx_dialogBox_hide
	});
}

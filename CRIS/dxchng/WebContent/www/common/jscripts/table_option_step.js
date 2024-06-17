function clickHiddenProcessButton() {
    document.getElementById('hiddenProcessButton_dint').click();
}

function process() {
    clickHiddenProcessButton();
}

function showTemplateResult(data) {
    showInformationPopup(data.message);
}

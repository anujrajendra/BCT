// Change File Import & Click Button Load File
document.getElementById("dintImportFile").addEventListener("change", dintChangeImportFile);
function dintChangeImportFile() {
    const inputTarget = document.getElementById("dintReUpload");
    inputTarget.value = "true";
    document.getElementById("dintButtonLoad").click();
}
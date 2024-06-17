function displayProducerOrConsumer(nodeType) {
    let producer = document.getElementById('serviceConfigurationProducer');
    let consumer = document.getElementById('serviceConfigurationConsumer');
    if (nodeType == `outbound`) {
        producer.style.display = 'table-row'
        consumer.style.display = 'none'
        return;
    }

    if (nodeType == `inbound`) {
        producer.style.display = 'none'
        consumer.style.display = 'table-row'
        return;
    }
}

function onChangeSpecificSetting(value) {
    let dataspaceDatasets = document.getElementById('serviceConfigurationDatagroups');
    if (value == 'false' || value === null) {
        toggleClearInputDataspaceDataset();
        dataspaceDatasets.style.display = 'table-row';
    } else {
        toggleClearInputDataspaceDataset();
        dataspaceDatasets.style.display = 'none';
    }
}

function toggleClearInputDataspaceDataset() {
    let allInputNeedReset = document.getElementById('serviceConfigurationDatagroups').querySelectorAll('li:not(.ebx_HidingContainerHidden)');
    allInputNeedReset.forEach(input => {
        let button = input.querySelector('button.ebx_Trashcan');
        if (button !== null && typeof button.onclick == 'function') {
            button.click();
        }
    }
    );
}

// Update topic value
function updateTopicsByProducer(producer, url) {
    if(!producer){
        // update topic option
        topicsID.forEach(topicId => {
            const topicElement = document.getElementById(topicId);
            removeOptions(topicElement);
            topicElement.add(createUndefinedOptionValue());
        });
        return;
    }

    // TODO: Add message to i18n
    const handleSuccess = res => {
        if(res.severity === 'ERROR'){
            ebx_alert(res.message);
            return;
        }
        const responseData = res.data ? res.data.sort(): [];
        // Update topic option
        topicsID.forEach(topicId => {
            const topicElement = document.getElementById(topicId);
            removeOptions(topicElement);
            topicElement.add(createUndefinedOptionValue());
            responseData.forEach(topic => {
                const option = document.createElement("option");
                option.text = topic;
                option.value = topic;
                topicElement.add(option);
            })
        });
    };
    const handleFail = res => {
        ebx_alert(res.message);
    };
    
    sendPOSTRequest(url,producer, handleSuccess, handleFail);
}

function onChangeDatamodels(datamodel, restUrl) {
    if(!datamodel){
        const tablePathSelect = document.querySelector("#serviceConfigurationTablePath select");

        removeOptions(tablePathSelect);
        tablePathSelect.add(createUndefinedOptionValue());
        return;
    }
    const handleSuccess = res => {
        const tablePathSelect = document.querySelector("#serviceConfigurationTablePath select");

        removeOptions(tablePathSelect);

        for (let j = 0; j < res.length; j++) {
            const value = res[j];
            const option = document.createElement("option");
            option.text = value;
            option.value = value;
            tablePathSelect.add(option);
        }
    };
    const handleFail = res => {
        const tablePathSelect = document.querySelector("#serviceConfigurationTablePath select");
        removeOptions(tablePathSelect);
        const option = document.createElement("option");
        option.text = "[not defined]";
        option.value = "";
        tablePathSelect.add(option);
    };

    sendPOSTRequest(restUrl, datamodel, handleSuccess, handleFail);
}

function onPreview(restUrl){
    // message format: {key: "", label: ""}
    const messageFormatValue = getMessageFormat();
    // dataModel, tablePath is selected value
    const dataModelValue = getDataModel();
    const tablePathValue = getDataTablePath();
    // validate input param here
    if(!messageFormatValue || !messageFormatValue.key || !dataModelValue || !tablePathValue){
        ebx_alert(previewErrorSelectRequiredFiled);
        return;
    }
    
    const data = {
        dataModel: dataModelValue,
        tablePath: tablePathValue,
        formatType: messageFormatValue.key
    };

    // TODO: Add message to i18n
    const handleSuccess = res => {
        const responseData = JSON.parse(res.data);
        const jsonFormat = '<pre id="json">'.concat(JSON.stringify(responseData, undefined, 2)).concat('</pre>');
        ebx_alert(previewMessageTitle,jsonFormat);
    };
    const handleFail = res => {
        ebx_alert(res.message);
    };
    
    sendPOSTRequest(restUrl,JSON.stringify(data), handleSuccess, handleFail);
}

function onChangeDataspace(dataspace, elementDataset) {
    const datasetSelect = document.getElementById(elementDataset);

    const handleSuccess = res => {
        removeOptions(datasetSelect);

        for (let j = 0; j < res.length; j++) {
            const label = res[j].label;
            const value = res[j].value;
            var option = document.createElement("option");
            option.text = label;
            option.value = value;
            datasetSelect.add(option);
        }
    };

    const handleFail = res => {
        removeOptions(datasetSelect);
        var option = document.createElement("option");
        option.text = "[not defined]";
        option.value = "";
        datasetSelect.add(option);
    };

    sendPOSTRequest(restDatagroupUrl, dataspace.id, handleSuccess, handleFail);
}

// Common function
// Create option with label not defined
function createUndefinedOptionValue(){
    const option = document.createElement("option");
    option.text = "[not defined]";
    option.value = "";
    return option;
}
// Remove all select option
function removeOptions(selectElement) {
    var i, L = selectElement.options.length - 1;
    for (i = L; i >= 0; i--) {
        selectElement.remove(i);
    }
}

function sendPOSTRequest(url, data, handleSuccess, handleFail) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var responseData = JSON.parse(this.response);
            if (responseData.severity === 'ERROR') {
                handleFail(responseData);
                return;
            }
            handleSuccess(responseData);
        }

        if (this.status == 500) {
            ebx_alert("500 Internal Server Error");
            return;
        }

    };

    xhttp.onerror = function (error) {
        ebx_alert(error);
    };
    xhttp.open('POST', url, true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.send(data);
}
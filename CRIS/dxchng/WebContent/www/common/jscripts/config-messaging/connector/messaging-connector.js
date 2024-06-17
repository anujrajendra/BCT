//var initialized in server
const connectorConfig = {

    getProperties: function () {
        let properties = [];
        for (let index = 0; index < connectorProperties.length; index++) {
            const connectorProperty = connectorProperties[index];
            if(connectorProperty.value != "null") {

                let selectorKey = document.getElementById(connectorProperty.key);
                let keyValue = selectorKey.options[selectorKey.selectedIndex].value;
    
                let selectorValue = document.getElementById(connectorProperty.value);
                let valueValue = selectorValue.value;
                if(valueValue) {
                    properties.push({
                        key: keyValue,
                        value: valueValue
                    })
                }
            }
        }
        return properties;
    },

    testConnection: function () {
        if (!connectorType()) {
          	ebx_alert(`Test connection failed :`,`Reason: Field 'Type' is mandatory.`);
            return;
        }
        
        const data = {
            connectorType: connectorType().key,
            properties: this.getProperties()
        };
        
        const handleSuccess = res => {
            ebx_alert(res.message);
        };
        const handleFail = res => {
            ebx_alert(`Test connection failed :`,`Reason: `+ res.message);
        };
        
        this.sendPOSTRequest(restUrl, JSON.stringify(data), handleSuccess, handleFail);
    },


    sendPOSTRequest: function (url, data, handleSuccess, handleFail) {
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

        xhttp.onerror = function(error) {
            ebx_alert(error);
        };
        xhttp.open('POST', url, true);
        xhttp.setRequestHeader('Content-type', 'application/json');
        xhttp.send(data);
    }
}
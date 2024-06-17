const ResultState = {
    INFO: 'INFO',
    WARNING: 'WARNING',
    ERROR: 'ERROR'
};

function testConnectionByRest(url, user, password, oldPassword, authentication, accessKey) {
    const params = {
        "url": url, "user": user,
        "password": password, "oldPassword": oldPassword,
        "authentication": authentication, "accessKey": accessKey
    };
    const handleSuccess = function (res) {
        ebx_alert(res.message);
    };
    const handleFail = function (res) {
        ebx_alert(res.message, res.detailMessage);
    }
    sendPostRequest(testConnectUrl, JSON.stringify(params), handleSuccess, handleFail);
}

function sendPostRequest(url, params, handleSuccess, handleFail) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var res = JSON.parse(this.response);
            if (res.state === ResultState.ERROR) {
                handleFail(res);
                return;
            }
            handleSuccess(res);
        }
    };
    xhttp.open('POST', url, true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.send(params);
}

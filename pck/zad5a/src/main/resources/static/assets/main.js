var app = (function () {

    function uploadXml() {
        var formData = new FormData();
        formData.append("myfile", document.getElementById("uploadingFile").files[0]);

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/file/upload");
        xhr.send(formData);
    }

    return {
        uploadXml: uploadXml
    }

})();
var myApp = angular.module('myApp',[]);

myApp.controller('appCtrl', MyCtrl);

MyCtrl.$inject = ['$scope', '$http'];

function MyCtrl($scope, $http) {

    $scope.data = null;

    function getData() {
        var formData = new FormData();
        formData.append("myfile", document.getElementById("uploadingFile").files[0]);

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/file/upload");
        xhr.send(formData);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                $scope.data = JSON.parse(xhr.responseText);
                console.log($scope.data);
                $scope.$apply();
            }
        };
    }

    function getOldData() {
        $http({
            method: 'GET',
            url: '/data'
        })
            .then(function (response) {
                $scope.data = response.data;
            });
    }

    function addRow() {
        $scope.selectedRow = {
            idKsiazki: null,
            tytul: "",
            isbn: "",
            autorKsiazki: {
                autor: null
            },
            dzialKsiazki: {
                dzial: null
            }
        };
        $scope.selectedIndex = -1;
    }

    function editRow(ksiazka, index) {
        $scope.selectedRow = angular.copy(ksiazka);
        $scope.selectedIndex = index;
    }

    function save() {

        $http({
            method: 'POST',
            url: '/data',
            data: $scope.selectedRow
        })
            .then(function (response) {
                if ($scope.selectedIndex === -1) {
                    $scope.data.ksiazki.push(response.data);
                } else {
                    $scope.data.ksiazki[$scope.selectedIndex] = response.data;
                }
            }, function () {
                console.log('save rejected');
            })
    }

    function cancel() {
        $scope.selectedIndex = null;
    }

    function deleteKsiazka(idKsiazki, index) {
        $http({
            method: 'DELETE',
            url: '/data/' + idKsiazki
        })
            .then(function () {
                $scope.data.ksiazki.splice(index, 1);
            });
    }

    $scope.$m = {
        getData: getData,
        editRow: editRow,
        save: save,
        cancel: cancel,
        addRow: addRow,
        deleteKsiazka: deleteKsiazka
    };

    getOldData();

}

// module
var phoneApp = angular.module('phoneApp', []);

phoneApp.controller('phoneAppController', function ($scope) {
    $scope.phones = ['1111111','2222222','3333333'];

    $scope.showNumbers=false;

    $scope.submit= function(){
        console.log("Hello World");
    }
});
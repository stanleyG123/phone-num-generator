// module
var phoneApp = angular.module('phoneApp', []);

phoneApp.factory('phoneDataSvc',['$http', function($http) {
    return {
        generate: function(phoneNum){
            let reqJson = {};
            reqJson.phoneNumber = phoneNum;
            reqJson.numberPerPage=10;
            return $http.post("/generate",reqJson);
        }
    }
}]);



phoneApp.controller('phoneAppController', function ($scope,phoneDataSvc) {

    $scope.phoneNumbers = [];
    $scope.showNumbers=false;
    $scope.phoneInput = "";


    $scope.submit= function(){
        phoneDataSvc.generate($scope.phoneInput).then(function(response){
            $scope.phoneNumbers = response.data;
            $scope.showNumbers=true;
        }, function (){
            console.log("error")
        });
    }
});
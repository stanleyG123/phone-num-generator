// module
var phoneApp = angular.module('phoneApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);

phoneApp.factory('phoneDataSvc',['$http', function($http) {
    return {
        generate: function(phoneNum,numberPerPage){
            let reqJson = {};
            reqJson.numberPerPage = numberPerPage;
            reqJson.phoneNumber = phoneNum;
            return $http.post("/generate",reqJson);
        },
        fetch:function(pageStart,pageEnd){
            let reqJson = {};
            reqJson.pageStart = pageStart;
            reqJson.pageEnd=pageEnd;

            return $http({
                url: "/fetchPage",
                method: "GET",
                params: reqJson
            });
        }
    }
}]);



phoneApp.controller('phoneAppController', function ($scope,phoneDataSvc) {

    $scope.phoneNumbers = [];
    $scope.showNumbers=false;
    $scope.phoneInput = "";
    $scope.totalItems = 0;
    $scope.itemsPerPage=5;
    $scope.totalNumber = 0;
    $scope.currentPage = 1;
    $scope.pageChanged = function(){
        let start = ($scope.currentPage - 1)* $scope.itemsPerPage;
        let end   = start + $scope.itemsPerPage;
        console.log("fetching start=" + start + " end=" + end)
        phoneDataSvc.fetch(start,end).then(function(response){
            $scope.phoneNumbers = response.data.combos;
        }, function (){
            console.log("error while fetching pages")
        });
    }

    $scope.submit= function(){
        phoneDataSvc.generate($scope.phoneInput,$scope.itemsPerPage).then(function(response){
            $scope.phoneNumbers = response.data.combos;
            $scope.showNumbers=true;
            $scope.totalNumber = response.data.numberOfCombos;
        }, function (){
            console.log("error while generating alphanumerics")
        });
    }
});
(function(){
    'use strict';
    angular.module('phoneApp').factory('PhoneDataSvc',phoneDataSvc);

    phoneDataSvc.$inject = ['$http'];
    function phoneDataSvc($http){
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
    }
})();

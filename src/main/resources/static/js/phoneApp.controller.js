/**
 * phone app controller definition
 */
(function(){
  'use strict';
  angular.module('phoneApp').controller('PhoneAppController',phoneAppController);

  phoneAppController.$inject = ['$scope','PhoneDataSvc'];

    /**
     * Controller function for phone app controller
     * @param $scope model-view object
     * @param PhoneDataSvc daata services provider for phone app ctrl
     */
  function phoneAppController($scope,PhoneDataSvc){
      $scope.phoneNumbers = [];
      $scope.showNumbers=false;
      $scope.phoneInput = "";
      $scope.totalItems = 0;
      $scope.itemsPerPage=10;
      $scope.totalNumber = 0;
      $scope.currentPage = 1;
      $scope.pageChanged = function(){
          let start = ($scope.currentPage - 1)* $scope.itemsPerPage;
          let end   = start + $scope.itemsPerPage;
          console.log("fetching start=" + start + " end=" + end)
          PhoneDataSvc.fetch(start,end).then(function(response){
              $scope.phoneNumbers = response.data.combos;
          }, function (){
              console.log("error while fetching pages")
          });
      }

      // generates phone number combinations
      $scope.submit= function(){
          $scope.phoneForm.phonenNumberInput.$setValidity("phonenNumberInput", true);
          if (validateNumber($scope.phoneInput)) {
              $scope.phoneInput = $scope.phoneInput.replace(/\D/g,'');
              PhoneDataSvc.generate($scope.phoneInput, $scope.itemsPerPage).then(function (response) {
                  $scope.phoneNumbers = response.data.combos;
                  $scope.showNumbers = true;
                  $scope.totalNumber = response.data.numberOfCombos;
              }, function () {
                  console.log("error while generating alphanumerics")
              });
          }else{
              $scope.phoneForm.phonenNumberInput.$setValidity("phonenNumberInput", false);
          }
      }
      // clears the screen
      $scope.clear = function(){
          $scope.phoneInput = "";
          $scope.showNumbers = false;
          $scope.phoneForm.phonenNumberInput.$setValidity("phonenNumberInput", true);
      }

      // utility function validates phone number
      function validateNumber(phoneNumberInput){
          let fixedNumber = phoneNumberInput.replace(/\D/g,'');
          if (fixedNumber.length === 7 || fixedNumber.length ===10 ){
              return true
          }else {
              return false;
          }
      }
  }
})();
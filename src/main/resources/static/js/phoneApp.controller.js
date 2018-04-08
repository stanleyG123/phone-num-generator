/**
 * phone app controller definition
 */
(function(){
  'use strict';
  angular.module('phoneApp').controller('PhoneAppController',phoneAppController);

  phoneAppController.$inject = ['$scope','PhoneDataSvc','ngToast'];

    /**
     * Controller function for phone app controller
     * @param $scope model-view object
     * @param PhoneDataSvc daata services provider for phone app ctrl
     */
  function phoneAppController($scope,PhoneDataSvc,ngToast){
      let cur = this;

      cur.phoneNumbers = [];
      cur.showNumbers=false;
      cur.phoneInput = "";
      cur.totalItems = 0;
      cur.itemsPerPage=10;
      cur.totalNumber = 0;
     // cur.viewPage = 1;
      cur.pageChanged = function(){
          let start = (cur.viewPage - 1)* cur.itemsPerPage;
          let end   = (start + cur.itemsPerPage) - 1;
          //console.log("fetching start=" + start + " end=" + end)
          PhoneDataSvc.fetch(start,end).then(function(response){
              cur.phoneNumbers = response.data.combos;
          }, function (){
              ngToast.danger({
                  content: '<p>Error while fetching page</p>'
              });
              console.log("error while fetching pages")
          });
      }

      // generates phone number combinations
      cur.submit= function(){
          if (validateNumber(cur.phoneInput)) {
              $scope.phoneForm.phonenNumberInput.$setValidity("phonenNumberInput", true);
              cur.phoneInput = cur.phoneInput.replace(/-|[^\d]/g, '');
              PhoneDataSvc.generate(cur.phoneInput, cur.itemsPerPage).then(function (response) {
                  cur.phoneNumbers = response.data.combos;
                  cur.showNumbers = true;
                  cur.totalNumber = response.data.numberOfCombos;
              }, function () {
                  ngToast.danger({
                      content: '<p>Error while generating alpha-numeric combinations</p>'
                  });
                  console.log("Error while generating alpha-numeric combinations")
              });
          }else{
              ngToast.danger('Bad phone number. A telephone number should be either 7 or 10 digits.');
              console.log('Bad phone number. The telephone number should be either 7 or 10 digits.')
              $scope.phoneForm.phonenNumberInput.$setValidity("phonenNumberInput", false);
          }
      }
      // clears the screen
      cur.clear = function(){
          ngToast.dismiss();
          cur.phoneInput = "";
          cur.showNumbers = false;
          $scope.phoneForm.phonenNumberInput.$setValidity("phonenNumberInput", true);
      }

      // utility function validates phone number
      function validateNumber(phoneNumberInput){
          if(typeof phoneNumberInput == 'undefined'){
              return false;
          }
          let fixedNumber = phoneNumberInput.replace(/-|[^\d-]/g, '');
          if (fixedNumber.length === 7 || fixedNumber.length ===10 ){
              return true
          }else {
              return false;
          }
      }
  }
})();
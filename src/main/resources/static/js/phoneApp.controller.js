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
              //todo: pop a ngToast
              //ngToast.create('Error while fetching alpha numeric combinations');

              ngToast.create({
                  className: 'warning',
                  content: '<a href="#" class="">a message</a>'
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
                  ngToast.create('Error while generating alpha numeric combinations');
                  console.log("error while generating alphanumerics")
              });
          }else{
              ngToast.create('Invalid Number , please fix');
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
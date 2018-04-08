/**
 * phone app module definition
 */
(function(){
    'use strict';
    let app = angular.module('phoneApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap','ngToast']);

    app.config(ngToastConfig);
    ngToastConfig.$inject = ['ngToastProvider'];

    function ngToastConfig(ngToastProvider){
        ngToastProvider.configure({
           animation:'slide',
           combineDuplications:true,
           maxNumber:20
        });
    }
})();
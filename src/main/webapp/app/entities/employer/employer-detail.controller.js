(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EmployerDetailController', EmployerDetailController);

    EmployerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employer'];

    function EmployerDetailController($scope, $rootScope, $stateParams, previousState, entity, Employer) {
        var vm = this;

        vm.employer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mblmsApp:employerUpdate', function(event, result) {
            vm.employer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

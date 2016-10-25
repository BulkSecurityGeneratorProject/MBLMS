(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EmployerController', EmployerController);

    EmployerController.$inject = ['$scope', '$state', 'Employer'];

    function EmployerController ($scope, $state, Employer) {
        var vm = this;
        
        vm.employers = [];

        loadAll();

        function loadAll() {
            Employer.query(function(result) {
                vm.employers = result;
            });
        }
    }
})();

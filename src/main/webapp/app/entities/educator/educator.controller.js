(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EducatorController', EducatorController);

    EducatorController.$inject = ['$scope', '$state', 'Educator'];

    function EducatorController ($scope, $state, Educator) {
        var vm = this;
        
        vm.educators = [];

        loadAll();

        function loadAll() {
            Educator.query(function(result) {
                vm.educators = result;
            });
        }
    }
})();

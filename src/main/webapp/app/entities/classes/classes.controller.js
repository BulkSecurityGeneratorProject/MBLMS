(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ClassesController', ClassesController);

    ClassesController.$inject = ['$scope', '$state', 'Classes'];

    function ClassesController ($scope, $state, Classes) {
        var vm = this;
        
        vm.classes = [];

        loadAll();

        function loadAll() {
            Classes.query(function(result) {
                vm.classes = result;
            });
        }
    }
})();

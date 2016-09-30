(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ClassesDetailController', ClassesDetailController);

    ClassesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Classes', 'User', 'School', 'Profiles'];

    function ClassesDetailController($scope, $rootScope, $stateParams, previousState, entity, Classes, User, School, Profiles) {
        var vm = this;

        vm.classes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mblmsApp:classesUpdate', function(event, result) {
            vm.classes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

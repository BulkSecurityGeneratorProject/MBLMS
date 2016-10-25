(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EducatorDetailController', EducatorDetailController);

    EducatorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Educator'];

    function EducatorDetailController($scope, $rootScope, $stateParams, previousState, entity, Educator) {
        var vm = this;

        vm.educator = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mblmsApp:educatorUpdate', function(event, result) {
            vm.educator = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

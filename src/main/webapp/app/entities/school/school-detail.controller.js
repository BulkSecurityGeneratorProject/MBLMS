(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('SchoolDetailController', SchoolDetailController);

    SchoolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'School', 'UserProperties'];

    function SchoolDetailController($scope, $rootScope, $stateParams, previousState, entity, School, UserProperties) {
        var vm = this;

        vm.school = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mblmsApp:schoolUpdate', function(event, result) {
            vm.school = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

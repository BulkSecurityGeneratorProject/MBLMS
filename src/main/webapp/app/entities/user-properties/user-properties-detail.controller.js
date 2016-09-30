(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('UserPropertiesDetailController', UserPropertiesDetailController);

    UserPropertiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserProperties', 'User', 'School'];

    function UserPropertiesDetailController($scope, $rootScope, $stateParams, previousState, entity, UserProperties, User, School) {
        var vm = this;

        vm.userProperties = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mblmsApp:userPropertiesUpdate', function(event, result) {
            vm.userProperties = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

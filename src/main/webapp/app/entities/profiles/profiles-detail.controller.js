(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ProfilesDetailController', ProfilesDetailController);

    ProfilesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profiles', 'User', 'Classes'];

    function ProfilesDetailController($scope, $rootScope, $stateParams, previousState, entity, Profiles, User, Classes) {
        var vm = this;

        vm.profiles = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mblmsApp:profilesUpdate', function(event, result) {
            vm.profiles = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

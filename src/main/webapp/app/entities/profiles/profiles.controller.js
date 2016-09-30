(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ProfilesController', ProfilesController);

    ProfilesController.$inject = ['$scope', '$state', 'Profiles'];

    function ProfilesController ($scope, $state, Profiles) {
        var vm = this;
        
        vm.profiles = [];

        loadAll();

        function loadAll() {
            Profiles.query(function(result) {
                vm.profiles = result;
            });
        }
    }
})();

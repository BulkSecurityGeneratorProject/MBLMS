(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('UserPropertiesController', UserPropertiesController);

    UserPropertiesController.$inject = ['$scope', '$state', 'UserProperties'];

    function UserPropertiesController ($scope, $state, UserProperties) {
        var vm = this;
        
        vm.userProperties = [];

        loadAll();

        function loadAll() {
            UserProperties.query(function(result) {
                vm.userProperties = result;
            });
        }
    }
})();

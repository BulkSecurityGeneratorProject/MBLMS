(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ProfilesDeleteController',ProfilesDeleteController);

    ProfilesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Profiles'];

    function ProfilesDeleteController($uibModalInstance, entity, Profiles) {
        var vm = this;

        vm.profiles = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Profiles.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

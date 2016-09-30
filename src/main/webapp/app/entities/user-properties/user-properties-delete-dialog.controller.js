(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('UserPropertiesDeleteController',UserPropertiesDeleteController);

    UserPropertiesDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserProperties'];

    function UserPropertiesDeleteController($uibModalInstance, entity, UserProperties) {
        var vm = this;

        vm.userProperties = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserProperties.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

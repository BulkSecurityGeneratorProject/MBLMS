(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EducatorDeleteController',EducatorDeleteController);

    EducatorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Educator'];

    function EducatorDeleteController($uibModalInstance, entity, Educator) {
        var vm = this;

        vm.educator = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Educator.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

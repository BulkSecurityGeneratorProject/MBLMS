(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EmployerDialogController', EmployerDialogController);

    EmployerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Employer'];

    function EmployerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Employer) {
        var vm = this;

        vm.employer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employer.id !== null) {
                Employer.update(vm.employer, onSaveSuccess, onSaveError);
            } else {
                Employer.save(vm.employer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mblmsApp:employerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

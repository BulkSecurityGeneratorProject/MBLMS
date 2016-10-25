(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('EducatorDialogController', EducatorDialogController);

    EducatorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Educator'];

    function EducatorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Educator) {
        var vm = this;

        vm.educator = entity;
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
            if (vm.educator.id !== null) {
                Educator.update(vm.educator, onSaveSuccess, onSaveError);
            } else {
                Educator.save(vm.educator, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mblmsApp:educatorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

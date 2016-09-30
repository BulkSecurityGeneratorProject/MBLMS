(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ClassesDialogController', ClassesDialogController);

    ClassesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Classes', 'User', 'School', 'Profiles'];

    function ClassesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Classes, User, School, Profiles) {
        var vm = this;

        vm.classes = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.schools = School.query();
        vm.profiles = Profiles.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classes.id !== null) {
                Classes.update(vm.classes, onSaveSuccess, onSaveError);
            } else {
                Classes.save(vm.classes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mblmsApp:classesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

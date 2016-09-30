(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('ProfilesDialogController', ProfilesDialogController);

    ProfilesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profiles', 'User', 'Classes'];

    function ProfilesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profiles, User, Classes) {
        var vm = this;

        vm.profiles = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.classes = Classes.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.profiles.id !== null) {
                Profiles.update(vm.profiles, onSaveSuccess, onSaveError);
            } else {
                Profiles.save(vm.profiles, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mblmsApp:profilesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

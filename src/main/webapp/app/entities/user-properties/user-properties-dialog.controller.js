(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('UserPropertiesDialogController', UserPropertiesDialogController);

    UserPropertiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserProperties', 'User', 'School'];

    function UserPropertiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserProperties, User, School) {
        var vm = this;

        vm.userProperties = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.schools = School.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userProperties.id !== null) {
                UserProperties.update(vm.userProperties, onSaveSuccess, onSaveError);
            } else {
                UserProperties.save(vm.userProperties, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mblmsApp:userPropertiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

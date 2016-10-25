(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'UserProperties'];

    function HomeController ($scope, Principal, LoginService, $state, UserProperties) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                return account;
            }).then(function(account){
                if(account != null)
//                    vm.userProprties = UserProperties.get({"id":"1"});
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();

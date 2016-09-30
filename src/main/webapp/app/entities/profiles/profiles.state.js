(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profiles', {
            parent: 'entity',
            url: '/profiles',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Profiles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profiles/profiles.html',
                    controller: 'ProfilesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('profiles-detail', {
            parent: 'entity',
            url: '/profiles/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Profiles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profiles/profiles-detail.html',
                    controller: 'ProfilesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Profiles', function($stateParams, Profiles) {
                    return Profiles.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profiles',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profiles-detail.edit', {
            parent: 'profiles-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profiles/profiles-dialog.html',
                    controller: 'ProfilesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profiles', function(Profiles) {
                            return Profiles.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profiles.new', {
            parent: 'profiles',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profiles/profiles-dialog.html',
                    controller: 'ProfilesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profiles', null, { reload: 'profiles' });
                }, function() {
                    $state.go('profiles');
                });
            }]
        })
        .state('profiles.edit', {
            parent: 'profiles',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profiles/profiles-dialog.html',
                    controller: 'ProfilesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profiles', function(Profiles) {
                            return Profiles.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profiles', null, { reload: 'profiles' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profiles.delete', {
            parent: 'profiles',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profiles/profiles-delete-dialog.html',
                    controller: 'ProfilesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Profiles', function(Profiles) {
                            return Profiles.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profiles', null, { reload: 'profiles' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

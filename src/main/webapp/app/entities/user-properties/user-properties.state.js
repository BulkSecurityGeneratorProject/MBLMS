(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-properties', {
            parent: 'entity',
            url: '/user-properties',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserProperties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-properties/user-properties.html',
                    controller: 'UserPropertiesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-properties-detail', {
            parent: 'entity',
            url: '/user-properties/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserProperties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-properties/user-properties-detail.html',
                    controller: 'UserPropertiesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserProperties', function($stateParams, UserProperties) {
                    return UserProperties.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-properties',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-properties-detail.edit', {
            parent: 'user-properties-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-properties/user-properties-dialog.html',
                    controller: 'UserPropertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserProperties', function(UserProperties) {
                            return UserProperties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-properties.new', {
            parent: 'user-properties',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-properties/user-properties-dialog.html',
                    controller: 'UserPropertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                educator: null,
                                gpa: null,
                                focus: null,
                                program: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-properties', null, { reload: 'user-properties' });
                }, function() {
                    $state.go('user-properties');
                });
            }]
        })
        .state('user-properties.edit', {
            parent: 'user-properties',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-properties/user-properties-dialog.html',
                    controller: 'UserPropertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserProperties', function(UserProperties) {
                            return UserProperties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-properties', null, { reload: 'user-properties' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-properties.delete', {
            parent: 'user-properties',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-properties/user-properties-delete-dialog.html',
                    controller: 'UserPropertiesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserProperties', function(UserProperties) {
                            return UserProperties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-properties', null, { reload: 'user-properties' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

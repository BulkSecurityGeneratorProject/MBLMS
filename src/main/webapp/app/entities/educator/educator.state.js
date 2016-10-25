(function() {
    'use strict';

    angular
        .module('mblmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('educator', {
            parent: 'entity',
            url: '/educator',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Educators'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/educator/educators.html',
                    controller: 'EducatorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('educator-detail', {
            parent: 'entity',
            url: '/educator/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Educator'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/educator/educator-detail.html',
                    controller: 'EducatorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Educator', function($stateParams, Educator) {
                    return Educator.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'educator',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('educator-detail.edit', {
            parent: 'educator-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educator/educator-dialog.html',
                    controller: 'EducatorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Educator', function(Educator) {
                            return Educator.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('educator.new', {
            parent: 'educator',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educator/educator-dialog.html',
                    controller: 'EducatorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('educator', null, { reload: 'educator' });
                }, function() {
                    $state.go('educator');
                });
            }]
        })
        .state('educator.edit', {
            parent: 'educator',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educator/educator-dialog.html',
                    controller: 'EducatorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Educator', function(Educator) {
                            return Educator.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('educator', null, { reload: 'educator' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('educator.delete', {
            parent: 'educator',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educator/educator-delete-dialog.html',
                    controller: 'EducatorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Educator', function(Educator) {
                            return Educator.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('educator', null, { reload: 'educator' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

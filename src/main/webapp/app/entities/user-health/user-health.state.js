(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-health', {
            parent: 'entity',
            url: '/user-health',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.userHealth.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-health/user-healths.html',
                    controller: 'UserHealthController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userHealth');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-health-detail', {
            parent: 'user-health',
            url: '/user-health/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.userHealth.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-health/user-health-detail.html',
                    controller: 'UserHealthDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userHealth');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserHealth', function($stateParams, UserHealth) {
                    return UserHealth.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-health',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-health-detail.edit', {
            parent: 'user-health-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-health/user-health-dialog.html',
                    controller: 'UserHealthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserHealth', function(UserHealth) {
                            return UserHealth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-health.new', {
            parent: 'user-health',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-health/user-health-dialog.html',
                    controller: 'UserHealthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                workHours: null,
                                doesWorkOut: null,
                                isSmoker: null,
                                isHealthFood: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-health', null, { reload: 'user-health' });
                }, function() {
                    $state.go('user-health');
                });
            }]
        })
        .state('user-health.edit', {
            parent: 'user-health',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-health/user-health-dialog.html',
                    controller: 'UserHealthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserHealth', function(UserHealth) {
                            return UserHealth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-health', null, { reload: 'user-health' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-health.delete', {
            parent: 'user-health',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-health/user-health-delete-dialog.html',
                    controller: 'UserHealthDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserHealth', function(UserHealth) {
                            return UserHealth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-health', null, { reload: 'user-health' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

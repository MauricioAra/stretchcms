(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('body-point-registry', {
            parent: 'entity',
            url: '/body-point-registry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.bodyPointRegistry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/body-point-registry/body-point-registries.html',
                    controller: 'BodyPointRegistryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bodyPointRegistry');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('body-point-registry-detail', {
            parent: 'body-point-registry',
            url: '/body-point-registry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.bodyPointRegistry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/body-point-registry/body-point-registry-detail.html',
                    controller: 'BodyPointRegistryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bodyPointRegistry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BodyPointRegistry', function($stateParams, BodyPointRegistry) {
                    return BodyPointRegistry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'body-point-registry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('body-point-registry-detail.edit', {
            parent: 'body-point-registry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point-registry/body-point-registry-dialog.html',
                    controller: 'BodyPointRegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BodyPointRegistry', function(BodyPointRegistry) {
                            return BodyPointRegistry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('body-point-registry.new', {
            parent: 'body-point-registry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point-registry/body-point-registry-dialog.html',
                    controller: 'BodyPointRegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('body-point-registry', null, { reload: 'body-point-registry' });
                }, function() {
                    $state.go('body-point-registry');
                });
            }]
        })
        .state('body-point-registry.edit', {
            parent: 'body-point-registry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point-registry/body-point-registry-dialog.html',
                    controller: 'BodyPointRegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BodyPointRegistry', function(BodyPointRegistry) {
                            return BodyPointRegistry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('body-point-registry', null, { reload: 'body-point-registry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('body-point-registry.delete', {
            parent: 'body-point-registry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point-registry/body-point-registry-delete-dialog.html',
                    controller: 'BodyPointRegistryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BodyPointRegistry', function(BodyPointRegistry) {
                            return BodyPointRegistry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('body-point-registry', null, { reload: 'body-point-registry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

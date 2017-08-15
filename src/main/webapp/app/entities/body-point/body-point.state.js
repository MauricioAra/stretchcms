(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('body-point', {
            parent: 'entity',
            url: '/body-point',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.bodyPoint.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/body-point/body-points.html',
                    controller: 'BodyPointController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bodyPoint');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('body-point-detail', {
            parent: 'body-point',
            url: '/body-point/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.bodyPoint.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/body-point/body-point-detail.html',
                    controller: 'BodyPointDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bodyPoint');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BodyPoint', function($stateParams, BodyPoint) {
                    return BodyPoint.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'body-point',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('body-point-detail.edit', {
            parent: 'body-point-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point/body-point-dialog.html',
                    controller: 'BodyPointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BodyPoint', function(BodyPoint) {
                            return BodyPoint.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('body-point.new', {
            parent: 'body-point',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point/body-point-dialog.html',
                    controller: 'BodyPointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                bodypart: null,
                                idbodypart: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('body-point', null, { reload: 'body-point' });
                }, function() {
                    $state.go('body-point');
                });
            }]
        })
        .state('body-point.edit', {
            parent: 'body-point',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point/body-point-dialog.html',
                    controller: 'BodyPointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BodyPoint', function(BodyPoint) {
                            return BodyPoint.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('body-point', null, { reload: 'body-point' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('body-point.delete', {
            parent: 'body-point',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/body-point/body-point-delete-dialog.html',
                    controller: 'BodyPointDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BodyPoint', function(BodyPoint) {
                            return BodyPoint.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('body-point', null, { reload: 'body-point' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-app', {
            parent: 'entity',
            url: '/user-app',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.userApp.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-app/user-apps.html',
                    controller: 'UserAppController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userApp');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-app-detail', {
            parent: 'user-app',
            url: '/user-app/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.userApp.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-app/user-app-detail.html',
                    controller: 'UserAppDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userApp');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserApp', function($stateParams, UserApp) {
                    return UserApp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-app',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-app-detail.edit', {
            parent: 'user-app-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-app/user-app-dialog.html',
                    controller: 'UserAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserApp', function(UserApp) {
                            return UserApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-app.new', {
            parent: 'user-app',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-app/user-app-dialog.html',
                    controller: 'UserAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                lastName: null,
                                age: null,
                                gender: null,
                                weight: null,
                                height: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-app', null, { reload: 'user-app' });
                }, function() {
                    $state.go('user-app');
                });
            }]
        })
        .state('user-app.edit', {
            parent: 'user-app',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-app/user-app-dialog.html',
                    controller: 'UserAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserApp', function(UserApp) {
                            return UserApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-app', null, { reload: 'user-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-app.delete', {
            parent: 'user-app',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-app/user-app-delete-dialog.html',
                    controller: 'UserAppDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserApp', function(UserApp) {
                            return UserApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-app', null, { reload: 'user-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
